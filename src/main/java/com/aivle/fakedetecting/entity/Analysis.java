package com.aivle.fakedetecting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Analysis extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_detection_history_id")
    private Long urlId;
    @ManyToOne
    @JoinColumn(name = "mb_seq")
    private Member member;
    @Column(name = "url")
    private String url;
    @Column(name = "url_result")
    private String result;
    @Column(name = "url_detection_rate")
    private Long rate;
    @Column(name = "url_detection_date")
    private String detectionDate;
    @Column(name = "url_title")
    private String title;
    @Column(name = "url_content")
    private String content;


}
