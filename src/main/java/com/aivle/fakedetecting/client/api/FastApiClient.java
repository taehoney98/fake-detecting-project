package com.aivle.fakedetecting.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "fastapiClient",
        url = "???" // 구축할 서버 주소
)
@Component
public interface FastApiClient {
    @GetMapping("/analysis")
    public String callAnalysis(@RequestParam("url") String request);

}
