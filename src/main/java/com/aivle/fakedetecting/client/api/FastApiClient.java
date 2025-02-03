package com.aivle.fakedetecting.client.api;

import com.aivle.fakedetecting.dto.AanalysisResult;
import com.aivle.fakedetecting.dto.ResponseAnalysis;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "fastApiClient",
        url = "http://localhost:8000" // 구축할 서버 주소
)
@Component
public interface FastApiClient {
    @GetMapping("/analysis")
    public AanalysisResult getAnalysis(@RequestParam("url") String request);
}
