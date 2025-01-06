package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.member.domain.Member;
import lte.backend.member.domain.MemberRole;
import lte.backend.member.dto.request.UpdateNicknameRequest;
import lte.backend.member.dto.request.UpdatePasswordRequest;
import lte.backend.util.IntegrationTest;
import lte.backend.util.WithMockCustomMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberIntegrationTest extends IntegrationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 닉네임 변경")
    void updateNickname() throws Exception {
        UpdateNicknameRequest request = getUpdateNicknameRequest();
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(put("/api/members/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        Member member = memberRepository.findById(1L).orElseThrow(AssertionError::new);
        assertThat(member.getNickname()).isEqualTo(request.nickname());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("409 : 닉네임 변경 - 현재와 동일한 닉네임")
    void updateNickname_DuplicateMyNickname() throws Exception {
        UpdateNicknameRequest request = new UpdateNicknameRequest(
                IntegrationFixture.testMember1.getNickname()
        );
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(put("/api/members/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("409 : 닉네임 변경 - 이미 존재하는 닉네임")
    void updateNickname_DuplicateOtherNickname() throws Exception {
        Member otherMember = Member.builder()
                .username("duplicate1234")
                .password("duplicate1234!!")
                .nickname("중복닉네임")
                .profileUrl("default_url")
                .role(MemberRole.ROLE_USER)
                .isDeleted(false)
                .build();
        memberRepository.save(otherMember);
        UpdateNicknameRequest request = new UpdateNicknameRequest(
                otherMember.getNickname()
        );

        String body = objectMapper.writeValueAsString(request);

        mvc.perform(put("/api/members/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 비밀번호 변경")
    void updatePassword() throws Exception {
        UpdatePasswordRequest request = new UpdatePasswordRequest(
                IntegrationFixture.testMember1.getPassword(),
                "test5678!!"
        );
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(put("/api/members/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        Member member = memberRepository.findById(1L).orElseThrow(AssertionError::new);
        assertThat(passwordEncoder.matches(request.newPassword(), member.getPassword())).isTrue();
    }

    @Test
    @WithMockCustomMember
    @DisplayName("400 : 비밀번호 변경 - 현재와 다른 비밀번호 입력")
    void updatePassword_InvalidPassword() throws Exception {
        UpdatePasswordRequest request = new UpdatePasswordRequest(
                IntegrationFixture.testMember1.getPassword() + "@",
                "test5678!!"
        );
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(put("/api/members/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }


    private UpdateNicknameRequest getUpdateNicknameRequest() {
        return new UpdateNicknameRequest(
                "테스트A2"
        );
    }
}
