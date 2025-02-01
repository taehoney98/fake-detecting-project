package com.aivle.fakedetecting.config.jwt;

import com.aivle.fakedetecting.entity.Admin;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.enums.Role;
import com.aivle.fakedetecting.repository.AdminRepository;
import com.aivle.fakedetecting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.startsWith("admin")) {
            Admin admin = adminRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
            return new MemberPrincipal(admin.getId(), admin.getEmail(), null, Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_ADMIN.getRole())));
        }
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new MemberPrincipal(member.getSeq(), member.getEmail(), null, Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.getRole())));
    }
}
