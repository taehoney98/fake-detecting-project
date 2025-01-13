package com.aivle.fakedetecting.repository;

import com.aivle.fakedetecting.entity.MailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<MailAuth, Long> {
}
