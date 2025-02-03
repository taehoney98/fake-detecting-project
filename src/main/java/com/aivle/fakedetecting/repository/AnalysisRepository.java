package com.aivle.fakedetecting.repository;

import com.aivle.fakedetecting.entity.Analysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    List<Analysis> findByMember_Seq(Long seq);

    Page<Analysis> findAllByMember_SeqOrderByCreateDateDesc(Long seq, Pageable pageable);
//    List<Analysis> findByMember_SeqOrderByCreateDateDesc(Long seq);
}
