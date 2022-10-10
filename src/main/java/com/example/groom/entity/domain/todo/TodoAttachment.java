package com.example.groom.entity.domain.todo;

import com.example.groom.entity.File;
import com.example.groom.entity.common.CoopEntity;
import com.example.groom.entity.domain.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoAttachment extends CoopEntity {

    @Column
    private String name;

    @Column
    private String explain;

    @JoinColumn(name = "file_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private File file;

    @JoinColumn(name = "todo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Todo todo;
}