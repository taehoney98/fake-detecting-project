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

    private String url;
    private String result;
    private Long rate;
    // 확인 필요 -> LocalDateTime
    private LocalDateTime detectionDate;
    private String title;
    private String content;
    private List<News> relatedNews;

// json
    public static ResponseAnalysis toResponseAnalysis(Analysis analysis){
        return ResponseAnalysis.builder()
                .url(analysis.getUrl())
                .result(analysis.getResult())
                .rate(analysis.getRate())
                .detectionDate(analysis.getDetectionDate())
                .title(analysis.getTitle())
                .content(analysis.getContent())
                .relatedNews(analysis.getNews())
                .build();
    }
}
