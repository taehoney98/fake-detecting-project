package com.aivle.fakedetecting.dto;

import com.aivle.fakedetecting.entity.Analysis;
import com.aivle.fakedetecting.entity.News;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseAnalysis {
    private Long id;
    private String url;
    private Float fakeNewsRate;
    private Float deepVoiceRate;
    private Float deepFakeRate;
    // 확인 필요 -> LocalDateTime
    private LocalDateTime detectionDate;
    private String title;
    private String content;
    private List<News> relatedNews;

// json
    public static ResponseAnalysis toResponseAnalysis(Analysis analysis){
        return ResponseAnalysis.builder()
                .id(analysis.getUrlId())
                .url(analysis.getUrl())
                .deepFakeRate(analysis.getDeepFakeRate())
                .deepVoiceRate(analysis.getDeepVoiceRate())
                .fakeNewsRate(analysis.getFakeNewsRate())
                .detectionDate(analysis.getCreateDate())
                .title(analysis.getTitle())
                .content(analysis.getContent())
                .relatedNews(analysis.getNews())
                .build();
    }
}
