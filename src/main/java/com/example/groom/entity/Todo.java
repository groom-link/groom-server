package com.example.groom.entity;


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

    @JoinColumn(name = "todoBox_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TodoBox todoBox;

    @JoinColumn(name = "userInfo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserInfo userInfo;
}
