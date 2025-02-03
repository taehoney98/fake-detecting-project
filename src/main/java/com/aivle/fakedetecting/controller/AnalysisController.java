package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.config.jwt.MemberPrincipal;
import com.aivle.fakedetecting.dto.AanalysisResult;
import com.aivle.fakedetecting.dto.ApiResult;
import com.aivle.fakedetecting.dto.ResponseAnalysis;
import com.aivle.fakedetecting.entity.Analysis;
import com.aivle.fakedetecting.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnalysisController {
    private final AnalysisService analysisService;

    @GetMapping("/analysis")
    public ApiResult<Boolean> getAnalysis(@RequestParam(name = "url") String url, @AuthenticationPrincipal MemberPrincipal memberPrincipal) throws Exception {
        analysisService.saveAnalysisResult(url, memberPrincipal.getUserId());

        return ApiResult.success(true, "성공");
    }

    @GetMapping("/analysis-history")
    public ApiResult<List<ResponseAnalysis>> responseAnalysisApiResult(@AuthenticationPrincipal MemberPrincipal memberPrincipal) throws Exception {
        List<Analysis> analysisList = analysisService.getAnalysisList(memberPrincipal.getUserId());
        List<ResponseAnalysis> responseAnalysisList = analysisList.stream().map(ResponseAnalysis::toResponseAnalysis).toList();
        return ApiResult.success(responseAnalysisList, "조회 성공");
    }
}
