package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.like.repository.LikeRepository;
import lte.backend.member.domain.Member;
import lte.backend.member.domain.MemberRole;
import lte.backend.post.domain.Post;
import lte.backend.post.repository.PostRepository;
import lte.backend.util.IntegrationTest;
import lte.backend.util.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikeIntegrationTest extends IntegrationTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;

    private Member member;
    private Post post;

    @BeforeEach
    void setup() {
        member = Member.builder()
                .id(1L)
                .username("test1234")
                .password(passwordEncoder.encode("test1234!!"))
                .nickname("나테스트")
                .profileUrl("default_url")
                .role(MemberRole.ROLE_USER)
                .isDeleted(false)
                .build();
        post = postRepository.save(IntegrationFixture.testPost(member));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("OK : 좋아요를 누름")
    void likePost() throws Exception {
        mvc.perform(post("/api/posts/" + post.getId() + "/like"))
                .andExpect(status().isOk());

        assertThat(likeRepository.existsByPostIdAndMemberId(post.getId(), member.getId())).isTrue();
        Post findPost = postRepository.findById(post.getId()).orElseThrow(AssertionError::new);
        assertThat(findPost.getLikeCount()).isEqualTo(1);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("409 : 같은 글에 좋아요를 두번 누름")
    void likePost_Duplicate() throws Exception {
        mvc.perform(post("/api/posts/" + post.getId() + "/like"))
                .andExpect(status().isOk());
        mvc.perform(post("/api/posts/" + post.getId() + "/like"))
                .andExpect(status().isConflict());
    }

}
