package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.client.api.FastApiClient;
import com.aivle.fakedetecting.dto.ResponseAnalysis;
import com.aivle.fakedetecting.entity.Analysis;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.repository.AnalysisRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final FastApiClient fastApiClient;
    private final MemberService memberService;
    private final AnalysisRepository analysisRepository;


    public List<ResponseAnalysis> requestAnalysis(String url) {
        return fastApiClient.getAnalysis(url);
    }

    // Analysis return
    @Transactional
    public void saveAnalysisResult(String url, Long memberId) throws Exception {
        Member member = memberService.findMember(memberId);

        List<ResponseAnalysis> responseList = fastApiClient.getAnalysis(url);
        List<Analysis> analyses = new ArrayList<>();

        for(ResponseAnalysis r : responseList){
            Analysis analysis = new Analysis();
            analysis.setUrl(r.getUrl());
            analysis.setResult(r.getResult());
            analysis.setContent(r.getContent());
            analysis.setTitle(r.getTitle());
            analysis.setRate(r.getRate());
            analysis.setDetectionDate(r.getDetectionDate());
            analysis.setMemberId(member);
            analysis.setNews(r.getRelatedNews());

            analyses.add(analysis);
        }
        analysisRepository.saveAll(analyses);
    }


}

