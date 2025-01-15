package com.aivle.fakedetecting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAnalysis {

    private String url;
    private String result;
    private Long rate;
    private String detectionDate;
    private String title;
    private String content;

    // TO DO:

}
