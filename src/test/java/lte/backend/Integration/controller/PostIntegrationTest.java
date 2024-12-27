package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.member.domain.Member;
import lte.backend.post.domain.Post;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.request.UpdatePostRequest;
import lte.backend.post.dto.response.CreatePostResponse;
import lte.backend.post.dto.response.GetPostResponse;
import lte.backend.post.repository.PostRepository;
import lte.backend.util.IntegrationTest;
import lte.backend.util.JsonMvcResponseMapper;
import lte.backend.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.stream.Stream;

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

        MvcResult result = mvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        CreatePostResponse response = JsonMvcResponseMapper.parseJsonResponse(result, CreatePostResponse.class);
        Post createdPost = postRepository.findById(response.postId()).orElseThrow(AssertionError::new);
        assertThat(createdPost.getTitle()).isEqualTo(request.title());
        assertThat(createdPost.getContent()).isEqualTo(request.content());
        assertThat(createdPost.isPrivate()).isEqualTo(request.isPrivate());
        assertThat(createdPost.getAutoDeleted()).isEqualTo(request.autoDeleted());
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
        String sql = "SELECT * FROM posts WHERE id = :id";
        Post deletedPost = (Post) entityManager.createNativeQuery(sql, Post.class)
                .setParameter("id", savedPost.getId())
                .getSingleResult();

        assertThat(postRepository.count()).isEqualTo(0);
        assertThat(deletedPost.isDeleted()).isTrue();
    }

    @Test
    @WithMockCustomUser
    @DisplayName("OK : 단건 게시글을 조회")
    void getPost() throws Exception {
        Member member = memberRepository.findById(1L).orElseThrow(AssertionError::new);
        Post savedPost = savePost(member);
        int firstViewCount = savedPost.getViewCount();

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

        assertThat(response.viewCount()).isEqualTo(firstViewCount + 1);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("OK : 여러번 조회 시 조회수가 증가")
    void validateUpdateViewCount() throws Exception {
        Member member = memberRepository.findById(1L).orElseThrow(AssertionError::new);
        Post savedPost = savePost(member);
        int firstViewCount = savedPost.getViewCount();

        mvc.perform(get("/api/posts/" + savedPost.getId()))
                .andExpect(status().isOk());
        MvcResult result = mvc.perform(get("/api/posts/" + savedPost.getId()))
                .andExpect(status().isOk())
                .andReturn();

        GetPostResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetPostResponse.class);
        assertThat(response.viewCount()).isEqualTo(firstViewCount + 2);
    }

    @ParameterizedTest
    @WithMockCustomUser
    @MethodSource("getCreatePostParameter")
    @DisplayName("ERROR : 게시글을 생성 - 요청에 대한 Validation 예외")
    void createPost_Error_Validation(CreatePostRequest request) throws Exception {
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomUser
    @DisplayName("ERROR : 게시글을 생성 - 자동 삭제 시간 오류")
    void createPost_Error_AutoDeleteTime() throws Exception {
        CreatePostRequest request = new CreatePostRequest(
                "테스트제목",
                "테스트내용",
                false,
                LocalDateTime.now().minusHours(1)
        );
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    private CreatePostRequest getCreatePostRequest() {
        return new CreatePostRequest(
                "테스트제목",
                "테스트내용",
                false,
                null
        );
    }

    private static Stream<Arguments> getCreatePostParameter() {
        return Stream.of(
                Arguments.of(new CreatePostRequest("", "테스트내용", false, null)),
                Arguments.of(new CreatePostRequest("123456789101112131415161718192021222324252627282930",
                        "테스트내용", false, null)),
                Arguments.of(new CreatePostRequest("테스트제목", "", false, null))
        );
    }

    private UpdatePostRequest getUpdatePostRequest() {
        return new UpdatePostRequest(
                "테스트수정제목",
                "테스트수정내용",
                false,
                null
        );
    }

    private Post savePost(Member member) {
        Post post = IntegrationFixture.testPost(member);
        return postRepository.save(post);
    }
}
