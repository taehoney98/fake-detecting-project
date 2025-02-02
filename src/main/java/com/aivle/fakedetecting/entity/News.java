package com.aivle.fakedetecting.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
//    @Column(name = "news_title")
//    private String title;
    @Column(name = "news_url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_detection_history_id")
    @JsonBackReference
    private Analysis analysis;

    public News (String url, Analysis analysis){
        this.url = url;
        this.analysis = analysis;
    }
}
