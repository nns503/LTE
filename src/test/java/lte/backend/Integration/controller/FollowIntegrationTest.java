package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.follow.domain.Follow;
import lte.backend.follow.dto.FollowDTO;
import lte.backend.follow.dto.response.GetFollowCountResponse;
import lte.backend.follow.dto.response.GetFolloweeListResponse;
import lte.backend.follow.dto.response.GetFolloweePostsResponse;
import lte.backend.follow.dto.response.GetFollowerListResponse;
import lte.backend.follow.repository.FollowRepository;
import lte.backend.member.domain.Member;
import lte.backend.post.repository.PostRepository;
import lte.backend.util.IntegrationTest;
import lte.backend.util.JsonMvcResponseMapper;
import lte.backend.util.WithMockCustomMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FollowIntegrationTest extends IntegrationTest {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private PostRepository postRepository;

    private Member member1;
    private Member member2;
    private Member member3;

    @BeforeEach
    void setUp() {
        member1 = IntegrationFixture.testMember1;
        member2 = memberRepository.save(IntegrationFixture.testMember2);
        member3 = memberRepository.save(IntegrationFixture.testMember3);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 팔로우 신청")
    void followMember() throws Exception {
        mvc.perform(post("/api/members/" + member2.getId() + "/follow"))
                .andExpect(status().isOk());
        assertThat(followRepository.existsByFolloweeIdAndFollowerId(member2.getId(), member1.getId())).isTrue();
    }

    @Test
    @WithMockCustomMember
    @DisplayName("409 : 팔로우 신청 - 이미 팔로우한 대상")
    void followMember_Duplicate() throws Exception {
        mvc.perform(post("/api/members/" + member2.getId() + "/follow"))
                .andExpect(status().isOk());
        mvc.perform(post("/api/members/" + member2.getId() + "/follow"))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 언팔로우 신청")
    void unfollowMember() throws Exception {
        mvc.perform(post("/api/members/" + member2.getId() + "/follow"))
                .andExpect(status().isOk());
        mvc.perform(delete("/api/members/" + member2.getId() + "/follow"))
                .andExpect(status().isOk());
        assertThat(followRepository.existsByFolloweeIdAndFollowerId(member2.getId(), member1.getId())).isFalse();
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 언팔로우 신청 - 대상의 팔로워가 아님")
    void unfollowMember_Not_Follower() throws Exception {
        mvc.perform(delete("/api/members/" + member2.getId() + "/follow"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 팔로워 리스트 조회")
    void getFollowerList() throws Exception {
        followRepository.save(Follow.builder()
                .followee(new Member(member1.getId()))
                .follower(new Member(member2.getId()))
                .build());
        followRepository.save(Follow.builder()
                .followee(new Member(member2.getId()))
                .follower(new Member(member1.getId()))
                .build());
        followRepository.save(Follow.builder()
                .followee(new Member(member1.getId()))
                .follower(new Member(member3.getId()))
                .build());
        MvcResult result = mvc.perform(get("/api/members/" + member1.getId() + "/follower"))
                .andExpect(status().isOk())
                .andReturn();

        GetFollowerListResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetFollowerListResponse.class);
        FollowDTO followDTO_member2 = new FollowDTO(member2.getId(), member2.getNickname(), true);
        FollowDTO followDTO_member3 = new FollowDTO(member3.getId(), member3.getNickname(), false);
        assertThat(response.sliceInfo().elements()).isEqualTo(2);
        assertThat(response.list()).hasSize(2);
        assertThat(response.list()).contains(followDTO_member2, followDTO_member3);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 팔로위 리스트 조회")
    void getFolloweeList() throws Exception {
        followRepository.save(Follow.builder()
                .followee(new Member(member2.getId()))
                .follower(new Member(member1.getId()))
                .build());
        followRepository.save(Follow.builder()
                .followee(new Member(member1.getId()))
                .follower(new Member(member2.getId()))
                .build());
        followRepository.save(Follow.builder()
                .followee(new Member(member3.getId()))
                .follower(new Member(member1.getId()))
                .build());
        MvcResult result = mvc.perform(get("/api/members/" + member1.getId() + "/followee"))
                .andExpect(status().isOk())
                .andReturn();

        GetFolloweeListResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetFolloweeListResponse.class);
        FollowDTO followDTO_member2 = new FollowDTO(member2.getId(), member2.getNickname(), true);
        FollowDTO followDTO_member3 = new FollowDTO(member3.getId(), member3.getNickname(), true);
        assertThat(response.sliceInfo().elements()).isEqualTo(2);
        assertThat(response.list()).hasSize(2);
        assertThat(response.list()).contains(followDTO_member2, followDTO_member3);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 팔로위 게시글 조회")
    void getFolloweePosts() throws Exception {
        followRepository.save(Follow.builder()
                .followee(new Member(member2.getId()))
                .follower(new Member(member1.getId()))
                .build());
        followRepository.save(Follow.builder()
                .followee(new Member(member3.getId()))
                .follower(new Member(member1.getId()))
                .build());
        postRepository.save(IntegrationFixture.testPost(member1));
        postRepository.save(IntegrationFixture.testPost(member2));
        postRepository.save(IntegrationFixture.testPost(member2));
        postRepository.save(IntegrationFixture.testPost(member3));
        postRepository.save(IntegrationFixture.testPost(member3));
        postRepository.save(IntegrationFixture.testPost(member3));

        MvcResult result = mvc.perform(get("/api/members/followee/posts"))
                .andExpect(status().isOk())
                .andReturn();
        GetFolloweePostsResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetFolloweePostsResponse.class);
        assertThat(response.posts()).hasSize(5);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 팔로워 및 팔로우 숫자 조회")
    void getFollowCount() throws Exception {
        followRepository.save(Follow.builder()
                .followee(new Member(member2.getId()))
                .follower(new Member(member1.getId()))
                .build());
        followRepository.save(Follow.builder()
                .followee(new Member(member3.getId()))
                .follower(new Member(member1.getId()))
                .build());
        followRepository.save(Follow.builder()
                .followee(new Member(member1.getId()))
                .follower(new Member(member2.getId()))
                .build());

        MvcResult result = mvc.perform(get("/api/members/" + member1.getId() + "/follow/count"))
                .andExpect(status().isOk())
                .andReturn();
        GetFollowCountResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetFollowCountResponse.class);
        assertThat(response.followeeCount()).isEqualTo(2);
        assertThat(response.followerCount()).isEqualTo(1);
    }
}
