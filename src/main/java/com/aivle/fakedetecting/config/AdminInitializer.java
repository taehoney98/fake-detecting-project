package com.aivle.fakedetecting.config;

import com.aivle.fakedetecting.entity.Admin;
import com.aivle.fakedetecting.entity.AdminRole;
import com.aivle.fakedetecting.repository.AdminRepository;
import com.aivle.fakedetecting.service.AdminRoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRoleService adminRoleService;
    @PostConstruct
    public void initAdmin() {
        Admin admin = new Admin();
        admin.setEmail("admin@aivle.com");
        admin.setPassword(passwordEncoder.encode("aivle2024"));
        AdminRole adminRole = adminRoleService.findAdminRole("ADMIN");
        admin.setAdmin_role(adminRole);
        if(adminRepository.findByEmail(admin.getEmail()).isEmpty()) {
            adminRepository.save(admin);
        }
    }
}
