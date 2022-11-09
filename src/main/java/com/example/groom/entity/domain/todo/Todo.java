package com.example.groom.entity.domain.todo;


import com.example.groom.domain.todo.Dto.TodoDetailDto;
import com.example.groom.domain.todo.Dto.TodoDto;
import com.example.groom.entity.common.CoopEntity;
import com.example.groom.entity.domain.auth.UserInfo;
import com.example.groom.entity.domain.room.Room;
import com.example.groom.entity.enums.RoomSlotSample;
import com.querydsl.core.annotations.QueryInit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Todo extends CoopEntity {

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private RoomSlotSample roomSlot;

    @Column
    private String fileUrl;

//    @JoinColumn
//    @ManyToOne(fetch = FetchType.LAZY)
//    private TodoBox todoBox;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @QueryInit("kakao.kakaoAccount.profile")
    private UserInfo todoOwner;

//    @JoinColumn
//    @ManyToOne(fetch = FetchType.LAZY)
//    private RoomSlot roomSlot;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    protected Todo(TodoDto todoDto) {
        this.title = todoDto.getTitle();
        this.content = todoDto.getContent();
        this.roomSlot = RoomSlotSample.todo;
        //this.todoBox = todoDto.getTodoBox();
        this.room = Room.of(todoDto.getRoomId());
        this.todoOwner = UserInfo.of(todoDto.getTodoOwnerId());
        //this.roomSlot = RoomSlot.of(todoDto.getRoomSlotId());
    }

    public Todo(Long id) {
        super(id);
    }


    protected Todo(TodoDetailDto todoDetailDto) {
        super(todoDetailDto.getId());
        this.title = todoDetailDto.getTitle();
        this.content = todoDetailDto.getContent();
        this.roomSlot = todoDetailDto.getRoomSlot();
        this.fileUrl = todoDetailDto.getFileUrl();
        this.todoOwner = UserInfo.of(todoDetailDto.getTodoOwnerId());
        this.fileUrl = todoDetailDto.getFileUrl();
    }

    public static Todo of(TodoDetailDto todoDetailDto) {
        return new Todo(todoDetailDto);
    }

    public static Todo of(TodoDto todoDto) {
        return new Todo(todoDto);
    }
}
