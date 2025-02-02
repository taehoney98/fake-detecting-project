package com.aivle.fakedetecting.entity;

import com.aivle.fakedetecting.dto.AanalysisResult;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Builder
public class Analysis extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_detection_history_id")
    private Long urlId;
    @ManyToOne
    @JoinColumn(name = "mb_seq")
    @JsonBackReference
    private Member member;
    @OneToMany(mappedBy = "analysis")
    private List<News> news;
    @Column(name = "url")
    private String url;
    @Column(name = "url_detection_fake_news_rate")
    private Float fakeNewsRate;
    @Column(name = "url_detection_deep_voice_rate")
    private Float deepVoiceRate;
    @Column(name = "url_detection_deep_fake_rate")
    private Float deepFakeRate;
    @Column(name = "url_title")
    private String title;
    @Column(name = "url_content")
    private String content;

    public static Analysis toAnalysis(AanalysisResult aanalysisResult) {
        return Analysis.builder()
                .fakeNewsRate(aanalysisResult.getFakeNewsRate())
                .deepVoiceRate(aanalysisResult.getDeepVoiceRate())
                .deepFakeRate(aanalysisResult.getDeepFakeRate())
                .title(aanalysisResult.getTitle())
                .content(aanalysisResult.getContent())
                .build();
    }

}
