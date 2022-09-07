package com.example.groom.entity;

import com.example.groom.entity.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Request extends CoopEntity{

    @Column
    private String content;

    @Column
    private LocalDateTime dueDate;

    @Column
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

//    @JoinColumn
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Long roomid;//bigint [ref: > room.id]

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Todo todo;
}