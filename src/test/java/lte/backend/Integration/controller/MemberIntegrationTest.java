package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.member.domain.Member;
import lte.backend.member.dto.request.UpdateNicknameRequest;
import lte.backend.member.dto.request.UpdatePasswordRequest;
import lte.backend.member.dto.response.GetMemberInfoResponse;
import lte.backend.util.IntegrationTest;
import lte.backend.util.JsonMvcResponseMapper;
import lte.backend.util.WithMockCustomMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberIntegrationTest extends IntegrationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Member member;

    @BeforeEach
    void setup() {
        member = IntegrationFixture.testMember1;
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 닉네임 변경")
    void updateNickname() throws Exception {
        UpdateNicknameRequest request = new UpdateNicknameRequest(
                "테스트A2"
        );
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
                member.getNickname()
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
        Member otherMember = IntegrationFixture.testMember2;
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
                member.getPassword(),
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
                member.getPassword() + "@",
                "test5678!!"
        );
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(put("/api/members/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 회원 정보 조회")
    void getMemberInfo() throws Exception {
        MvcResult result = mvc.perform(get("/api/members/" + member.getId() + "/info"))
                .andExpect(status().isOk())
                .andReturn();

        GetMemberInfoResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetMemberInfoResponse.class);
        assertThat(response.nickname()).isEqualTo(member.getNickname());
        assertThat(response.profileUrl()).isEqualTo(member.getProfileUrl());
    }
}
