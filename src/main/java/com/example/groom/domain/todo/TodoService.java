package com.example.groom.domain.todo;

import com.example.groom.common.exception.CustomException;
import com.example.groom.common.exception.ErrorCode;
import com.example.groom.domain.auth.userInfo.UserInfoRepository;
import com.example.groom.domain.todo.Dto.TodoDetailDto;
import com.example.groom.domain.todo.Dto.TodoDto;
import com.example.groom.domain.todo.Dto.TodoListResponseDto;
import com.example.groom.domain.todo.Dto.TodoSearchCondition;
import com.example.groom.domain.todo.Repository.TodoRepository;
import com.example.groom.entity.domain.auth.UserInfo;
import com.example.groom.entity.domain.todo.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    private final UserInfoRepository userInfoRepository;

    public TodoDetailDto getTodoDetail(Long id) {
        return TodoDetailDto.of(this.todoRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TODO_NOT_FOUND)));
    }

    public TodoListResponseDto searchByCondition(TodoSearchCondition todoSearchCondition, Pageable pageable) {
        // 리스트는 널값 체크 안해도 됨
        return this.todoRepository.searchByCondition(todoSearchCondition, pageable);
    }

    @Transactional
    public TodoDetailDto createTodo(TodoDto todoDto) {
        Todo todo = this.todoRepository.save(Todo.of(todoDto));

        UserInfo todoOwner = userInfoRepository.findById(todoDto.getTodoOwnerId()).get();

        return TodoDetailDto.of(todo, todoOwner);
    }

    public void deleteTodo(Long id) {

        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
        } else {
            throw new CustomException(ErrorCode.SCHEDULE_NOT_FOUND);
        }
    }

    public TodoDetailDto updateTodo(TodoDetailDto todoDetailDto) {
        return TodoDetailDto.of(this.todoRepository.save(Todo.of(todoDetailDto)));
    }
}
