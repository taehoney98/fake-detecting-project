package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.config.jwt.JwtUtil;
import com.aivle.fakedetecting.dto.*;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.enums.Role;
import com.aivle.fakedetecting.error.CustomException;
import com.aivle.fakedetecting.error.MemberNotFound;
import com.aivle.fakedetecting.repository.MemberRepository;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("Successful sign-up")
    void signUp_Success() throws Exception {
        // Arrange
        RequestSignUp requestSignUp = new RequestSignUp();
        requestSignUp.setEmail("test@example.com");
        requestSignUp.setInfoAgmt(true);
        requestSignUp.setSvcAgmt(true);

        when(memberRepository.findByEmail(requestSignUp.getEmail())).thenReturn(Optional.empty());
        Member mockMember = Member.builder().email(requestSignUp.getEmail()).build();
        when(memberRepository.save(any(Member.class))).thenReturn(mockMember);

        // Act
        Member result = memberService.signUp(requestSignUp);

        // Assert
        assertEquals(requestSignUp.getEmail(), result.getEmail());
        verify(memberRepository, times(1)).findByEmail(requestSignUp.getEmail());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("Sign-up fails due to missing agreement")
    void signUp_MissingAgreement_Fails() {
        // Arrange
        RequestSignUp requestSignUp = new RequestSignUp();
        requestSignUp.setEmail("test@example.com");
        requestSignUp.setInfoAgmt(false);
        requestSignUp.setSvcAgmt(false);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> memberService.signUp(requestSignUp));
        assertEquals("개인정보 수집 및 서비스 이용약관에 동의하지 않았습니다.", exception.getMessage());
        verify(memberRepository, never()).findByEmail(anyString());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("Sign-up fails due to existing email")
    void signUp_EmailAlreadyExists_Fails() {
        // Arrange
        RequestSignUp requestSignUp = new RequestSignUp();
        requestSignUp.setEmail("test@example.com");
        requestSignUp.setInfoAgmt(true);
        requestSignUp.setSvcAgmt(true);

        when(memberRepository.findByEmail(requestSignUp.getEmail()))
                .thenReturn(Optional.of(new Member()));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> memberService.signUp(requestSignUp));
        assertEquals("이미 가입된 이메일 입니다.", exception.getMessage());
        verify(memberRepository, times(1)).findByEmail(requestSignUp.getEmail());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("Password change fails when new password and confirm password do not match")
    void changePassword_PasswordsDoNotMatch_Fails() {
        // Arrange
        Long memberId = 1L;
        RequestChangePassword requestChangePassword = new RequestChangePassword();
        requestChangePassword.setCurrentPassword("currentPassword");
        requestChangePassword.setNewPassword("newPassword");
        requestChangePassword.setConfirmPassword("differentPassword");

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> memberService.changePassword(memberId, requestChangePassword));
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());
        verify(memberRepository, never()).findById(memberId);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Password change fails when member is not found")
    void changePassword_MemberNotFound_Fails() {
        // Arrange
        Long memberId = 1L;
        RequestChangePassword requestChangePassword = new RequestChangePassword();
        requestChangePassword.setCurrentPassword("currentPassword");
        requestChangePassword.setNewPassword("newPassword");
        requestChangePassword.setConfirmPassword("newPassword");

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(MemberNotFound.class, () -> memberService.changePassword(memberId, requestChangePassword));
        verify(memberRepository, times(1)).findById(memberId);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Successful member retrieval")
    void findMember_Success() {
        // Arrange
        Long memberId = 1L;
        Member mockMember = Member.builder()
                .seq(memberId)
                .email("test@example.com")
                .build();

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

        // Act
        Member result = memberService.findMember(memberId);

        // Assert
        assertEquals(memberId, result.getSeq());
        assertEquals("test@example.com", result.getEmail());
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("Member not found")
    void findMember_NotFound_Fails() {
        // Arrange
        Long memberId = 1L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(MemberNotFound.class, () -> memberService.findMember(memberId));
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("Successful profile update")
    void updateProfile_Success() {
        // Arrange
        Long memberId = 1L;
        RequestProfile requestProfile = new RequestProfile();
        requestProfile.setPhone("+82 010-0000-0000");
        requestProfile.setNickName("New Nickname");

        Member mockMember = Member.builder()
                .seq(memberId)
                .phone("+1 010-0000-0000")
                .nickName("Old Nickname")
                .build();

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

        // Act
        Member result = memberService.updateProfile(memberId, requestProfile);

        // Assert
        assertEquals("+82 010-0000-0000", result.getPhone());
        assertEquals("New Nickname", result.getNickName());
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("Profile update fails when member not found")
    void updateProfile_MemberNotFound_Fails() {
        // Arrange
        Long memberId = 1L;
        RequestProfile requestProfile = new RequestProfile();
        requestProfile.setPhone("+82 010-0000-0000");
        requestProfile.setNickName("New Nickname");

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(MemberNotFound.class, () -> memberService.updateProfile(memberId, requestProfile));
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("Successful login")
    void login_Success() {
        // Arrange
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail("test@example.com");
        requestLogin.setPassword("correctPassword");

        Member mockMember = Member.builder()
                .seq(1L)
                .email("test@example.com")
                .password("$2a$10$abcdefghijklmnopqrstuvwx") // Encoded password
                .build();

        when(memberRepository.findByEmail(requestLogin.getEmail())).thenReturn(Optional.of(mockMember));
        when(passwordEncoder.matches(requestLogin.getPassword(), mockMember.getPassword())).thenReturn(true);
        when(jwtUtil.createJwt(1L, "test@example.com", 24 * 60 * 60 * 1000L, Role.ROLE_USER))
                .thenReturn("MockedJWTToken");

        // Act
        ResponseLogin result = memberService.login(requestLogin);

        // Assert
        assertNotNull(result);
        assertEquals("MockedJWTToken", result.getToken());
        verify(memberRepository, times(1)).findByEmail(requestLogin.getEmail());
        verify(passwordEncoder, times(1)).matches(requestLogin.getPassword(), mockMember.getPassword());
    }

    @Test
    @DisplayName("Login fails due to invalid credentials")
    void login_InvalidCredentials_Fail() {
        // Arrange
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail("test@example.com");
        requestLogin.setPassword("wrongPassword");

        Member mockMember = Member.builder()
                .seq(1L)
                .email("test@example.com")
                .password("$2a$10$abcdefghijklmnopqrstuvwx") // Encoded password
                .build();

        when(memberRepository.findByEmail(requestLogin.getEmail())).thenReturn(Optional.of(mockMember));
        when(passwordEncoder.matches(requestLogin.getPassword(), mockMember.getPassword())).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(CustomException.class, () -> memberService.login(requestLogin));
        assertEquals("이메일 혹은 비밀번호를 잘못 입력하셨거나 등록되지 않은 이메일 입니다.", exception.getMessage());
        verify(memberRepository, times(1)).findByEmail(requestLogin.getEmail());
        verify(passwordEncoder, times(1)).matches(requestLogin.getPassword(), mockMember.getPassword());
    }

    @Test
    @DisplayName("Login fails due to non-existing member")
    void login_MemberNotFound_Fail() {
        // Arrange
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail("nonexistent@example.com");
        requestLogin.setPassword("anyPassword");

        when(memberRepository.findByEmail(requestLogin.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(CustomException.class, () -> memberService.login(requestLogin));
        assertEquals("이메일 혹은 비밀번호를 잘못 입력하셨거나 등록되지 않은 이메일 입니다.", exception.getMessage());
        verify(memberRepository, times(1)).findByEmail(requestLogin.getEmail());
        verifyNoInteractions(passwordEncoder);
    }
}