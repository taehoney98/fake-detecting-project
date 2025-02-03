package com.aivle.fakedetecting.repository;

import com.aivle.fakedetecting.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {

    AdminRole findByRoleName(String roleName);
}
