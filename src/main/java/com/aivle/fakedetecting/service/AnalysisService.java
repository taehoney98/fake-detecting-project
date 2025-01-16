package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.client.api.FastApiClient;
import com.aivle.fakedetecting.dto.ResponseAnalysis;
import com.aivle.fakedetecting.entity.Analysis;
import com.aivle.fakedetecting.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final FastApiClient fastApiClient;
    private final MemberService memberService;

    // Analysis return
    @Transactional
    public void getResult(@RequestParam("url") String url, Long id, ResponseAnalysis analysisResponseAnalysis) throws Exception{
        Member member = memberService.findMember(id);
        ResponseAnalysis responseAnalysis = fastApiClient.getAnalysis(url);
    }

}

}
// front end 이번주 안에 금요일빼고 목요일
// 관리자 로그인
// 에러 페이지 확인
