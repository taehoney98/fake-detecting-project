package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.client.api.FastApiClient;
import com.aivle.fakedetecting.dto.AanalysisResult;
import com.aivle.fakedetecting.dto.ResponseAnalysis;
import com.aivle.fakedetecting.entity.Analysis;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.entity.News;
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
    private final NewsService newsService;

//    public List<ResponseAnalysis> requestAnalysis(String url) {
//        return fastApiClient.getAnalysis(url);
//    }

    // Analysis return
    @Transactional
    public void saveAnalysisResult(String url, Long memberId) throws Exception {
        Member member = memberService.findMember(memberId);

        AanalysisResult analysisResult = fastApiClient.getAnalysis(url);
        Analysis analysis = Analysis.toAnalysis(analysisResult);
        analysis.setUrl(url);
        analysis.setMember(member);
        Analysis savedAnalysis = analysisRepository.save(analysis);
        List<News> newsList = analysisResult.getNews().stream().map(s -> new News(s, savedAnalysis)).toList();
        if(savedAnalysis.getNews() == null){
            savedAnalysis.setNews(new ArrayList<>());
        }
        savedAnalysis.getNews().addAll(newsService.saveAll(newsList));
//        List<Analysis> analyses = new ArrayList<>();
//
//        for(ResponseAnalysis r : responseList){
//            Analysis analysis = new Analysis();
//            analysis.setUrl(r.getUrl());
//            analysis.setResult(r.getResult());
//            analysis.setContent(r.getContent());
//            analysis.setTitle(r.getTitle());
//            analysis.setRate(r.getRate());
//            analysis.setDetectionDate(r.getDetectionDate());
//            analysis.setMemberId(member);
//            analysis.setNews(r.getRelatedNews());
//
//            analyses.add(analysis);
//        }
//        analysisRepository.saveAll(analyses);
    }

    public List<Analysis> getAnalysisList(Long memberId) {
        return analysisRepository.findByMember_SeqOrderByCreateDateDesc(memberId);
    }
}

