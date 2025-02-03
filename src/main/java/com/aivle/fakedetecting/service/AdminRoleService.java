package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.entity.AdminRole;
import com.aivle.fakedetecting.repository.AdminRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRoleService {
    private final AdminRoleRepository adminRoleRepository;

    public AdminRole findAdminRole(String roleName) {
        return adminRoleRepository.findByRoleName(roleName);
    }
}
