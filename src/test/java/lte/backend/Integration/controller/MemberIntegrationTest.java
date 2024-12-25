package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.member.domain.Member;
import lte.backend.member.dto.request.UpdateNicknameRequest;
import lte.backend.member.dto.request.UpdatePasswordRequest;
import lte.backend.util.IntegrationTest;
import lte.backend.util.WithMockCustomUser;
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
    @WithMockCustomUser
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
    @WithMockCustomUser
    @DisplayName("OK : 비밀번호 변경")
    void updatePassword() throws Exception {
        UpdatePasswordRequest request = new UpdatePasswordRequest(
                IntegrationFixture.testMember.getPassword(),
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

    private UpdateNicknameRequest getUpdateNicknameRequest() {
        return new UpdateNicknameRequest(
                "테스트A2"
        );
    }
}
