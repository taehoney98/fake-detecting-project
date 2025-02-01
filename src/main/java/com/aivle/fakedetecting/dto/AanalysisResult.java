package com.aivle.fakedetecting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AanalysisResult {
    private String title;
    private String content;
    private Float fakeNewsRate;
    private List<String> news;
    private Float deepVoiceRate;
    private Float deepFakeRate;
}
