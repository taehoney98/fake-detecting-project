package com.aivle.fakedetecting.repository;

import com.aivle.fakedetecting.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // TODO: 필요 시 쿼리 수정 필요, jpql, query dsl 등 선택, fetch join
}
