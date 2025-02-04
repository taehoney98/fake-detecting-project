package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.config.jwt.MemberPrincipal;
import com.aivle.fakedetecting.dto.AanalysisResult;
import com.aivle.fakedetecting.dto.ApiResult;
import com.aivle.fakedetecting.dto.ResponseAnalysis;
import com.aivle.fakedetecting.entity.Analysis;
import com.aivle.fakedetecting.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ApiResult<Page<ResponseAnalysis>> responseAnalysisApiResult(@RequestParam(name = "page", defaultValue = "0") String page
            ,@AuthenticationPrincipal MemberPrincipal memberPrincipal) throws Exception {
        Page<Analysis> analysisList = analysisService.getAnalysisList(memberPrincipal.getUserId(), Integer.parseInt(page));
        Page<ResponseAnalysis> responseAnalysis = analysisList.map(ResponseAnalysis::toResponseAnalysis);
        return ApiResult.success(responseAnalysis, "조회 성공");
    }
}
