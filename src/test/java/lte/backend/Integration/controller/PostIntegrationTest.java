package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntergrationFixture;
import lte.backend.member.domain.Member;
import lte.backend.post.domain.Post;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.request.UpdatePostRequest;
import lte.backend.post.dto.response.GetPostResponse;
import lte.backend.post.repository.PostRepository;
import lte.backend.util.IntegrationTest;
import lte.backend.util.JsonMvcResponseMapper;
import lte.backend.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostIntegrationTest extends IntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @WithMockCustomUser
    @DisplayName("OK : 게시글을 생성")
    void createPost() throws Exception {
        CreatePostRequest request = getCreatePostRequest();
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        assertThat(memberRepository.count()).isEqualTo(1);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("OK : 게시글을 수정")
    void updatePost() throws Exception {
        Member member = memberRepository.findById(1L).orElseThrow(AssertionError::new);
        Post savedPost = savePost(member);
        UpdatePostRequest request = getUpdatePostRequest();
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(put("/api/posts/" + savedPost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        Post updatedPost = postRepository.findById(savedPost.getId()).orElseThrow(AssertionError::new);
        assertThat(updatedPost.getTitle()).isEqualTo(request.title());
        assertThat(updatedPost.getContent()).isEqualTo(request.content());
        assertThat(updatedPost.isPrivate()).isEqualTo(request.isPrivate());
        assertThat(updatedPost.getAutoDeleted()).isEqualTo(request.autoDeleted());
    }

    @Test
    @WithMockCustomUser
    @DisplayName("OK : 게시글을 삭제")
    void deletePost() throws Exception {
        Member member = memberRepository.findById(1L).orElseThrow(AssertionError::new);
        Post savedPost = savePost(member);

        mvc.perform(delete("/api/posts/" + savedPost.getId()))
                .andExpect(status().isOk());

        assertThat(postRepository.count()).isEqualTo(1);
        assertThat(postRepository.findById(savedPost.getId()).isPresent()).isTrue();
        assertThat(postRepository.findById(savedPost.getId()).get().isDeleted()).isTrue();
    }

    @Test
    @WithMockCustomUser
    @DisplayName("OK : 단건 게시글을 조회")
    void getPost() throws Exception {
        Member member = memberRepository.findById(1L).orElseThrow(AssertionError::new);
        Post savedPost = savePost(member);

        MvcResult result = mvc.perform(get("/api/posts/" + savedPost.getId()))
                .andExpect(status().isOk())
                .andReturn();

        GetPostResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetPostResponse.class);
        assertThat(response.title()).isEqualTo(savedPost.getTitle());
        assertThat(response.content()).isEqualTo(savedPost.getContent());
        assertThat(response.viewCount()).isEqualTo(savedPost.getViewCount());
        assertThat(response.likeCount()).isEqualTo(savedPost.getLikeCount());
        assertThat(response.isPrivate()).isEqualTo(savedPost.isPrivate());
        assertThat(response.autoDeleted()).isEqualTo(savedPost.getAutoDeleted());
        assertThat(response.createdAt()).isEqualTo(savedPost.getCreatedAt());
        assertThat(response.nickname()).isEqualTo(savedPost.getMember().getNickname());
        assertThat(response.memberId()).isEqualTo(savedPost.getMember().getId());
    }

    private UpdatePostRequest getUpdatePostRequest() {
        return new UpdatePostRequest(
                "테스트수정제목",
                "테스트수정내용",
                false,
                null
        );
    }

    private CreatePostRequest getCreatePostRequest() {
        return new CreatePostRequest(
                "테스트제목",
                "테스트내용",
                false,
                null
        );
    }

    private Post savePost(Member member) {
        Post post = IntergrationFixture.testPost(member);
        return postRepository.save(post);
    }
}
