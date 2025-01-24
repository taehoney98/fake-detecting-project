package com.aivle.fakedetecting.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;
    @Column(name = "news_title")
    private String title;
    @Column(name = "news_url")
    private String url;

    //TODO: url탐지내역 연관관계 MaynToOne
}
