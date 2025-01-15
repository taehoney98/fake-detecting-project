package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.client.api.FastApiClient;
import com.aivle.fakedetecting.dto.AnalysisResult;
import com.aivle.fakedetecting.entity.Analysis;
import com.aivle.fakedetecting.repository.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final FastApiClient fastApiClient;
    private final AnalysisRepository analysisRepository;
    private final AnalysisService analysisService;


    // TO DO
    public void saveAnalysis(AnalysisResult analysisResult, Long urlId, String request){


    }

}
// front end 이번주 안에 금요일빼고 목요일
// 관리자 로그인
// 에러 페이지 확인
