package com.aivle.fakedetecting.repository;

import com.aivle.fakedetecting.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNameAndPhone(String name, String phone);

    Optional<Member> findByNameAndEmailAndPhone(String name, String email, String phone);
}
