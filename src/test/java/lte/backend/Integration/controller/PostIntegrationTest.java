package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.member.domain.Member;
import lte.backend.post.domain.Post;
import lte.backend.post.dto.PostDTO;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.request.UpdatePostRequest;
import lte.backend.post.dto.response.CreatePostResponse;
import lte.backend.post.dto.response.GetPostResponse;
import lte.backend.post.dto.response.GetPostsResponse;
import lte.backend.post.repository.PostRepository;
import lte.backend.util.IntegrationTest;
import lte.backend.util.JsonMvcResponseMapper;
import lte.backend.util.WithMockCustomMember;
import lte.backend.util.formatter.CustomTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostIntegrationTest extends IntegrationTest {

    @Autowired
    private PostRepository postRepository;

    private Member member;

    @BeforeEach
    void setup() {
        member = IntegrationFixture.testMember1;
    }

    @Test
    @WithMockCustomMember
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
    @WithMockCustomMember
    @DisplayName("OK : 게시글을 수정")
    void updatePost() throws Exception {
        member = memberRepository.findById(1L).orElseThrow(AssertionError::new);
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
    @WithMockCustomMember
    @DisplayName("OK : 게시글을 삭제")
    void deletePost() throws Exception {
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
    @WithMockCustomMember
    @DisplayName("OK : 단건 게시글을 조회")
    void getPost() throws Exception {
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
        assertThat(response.autoDeleted()).isEqualTo(CustomTimeFormatter.formatToDateTime(savedPost.getAutoDeleted()));
        assertThat(response.createdAt()).isEqualTo(CustomTimeFormatter.formatToDateTime(savedPost.getCreatedAt()));
        assertThat(response.nickname()).isEqualTo(savedPost.getMember().getNickname());
        assertThat(response.memberId()).isEqualTo(savedPost.getMember().getId());

        assertThat(response.viewCount()).isEqualTo(firstViewCount + 1);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 여러번 조회 시 조회수가 증가")
    void validateUpdateViewCount() throws Exception {
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

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 게시글 목록 조회 - 기본값 , DATE")
    void getPosts_Default_DATE() throws Exception {
        List<Post> posts = savePosts(member);
        List<Post> descCreateAtPosts = posts.stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .toList();
        List<PostDTO> postDTOS = toPostDTOS(descCreateAtPosts);

        MvcResult result = mvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andReturn();

        GetPostsResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetPostsResponse.class);
        assertThat(response.posts()).isEqualTo(postDTOS);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 게시글 목록 조회 - 조회수")
    void getPosts_VIEW_COUNT() throws Exception {
        List<Post> posts = savePosts(member);
        List<Post> descCreateAtPosts = posts.stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .toList();
        List<PostDTO> postDTOS = toPostDTOS(descCreateAtPosts);

        MvcResult result = mvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andReturn();

        GetPostsResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetPostsResponse.class);
        assertThat(response.posts()).isEqualTo(postDTOS);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 게시글 목록 조회 - 좋아요")
    void getPosts_LIKES() throws Exception {
        List<Post> posts = savePosts(member);
        List<Post> descLikeCountPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getLikeCount, Comparator.reverseOrder())
                        .thenComparing(Post::getId, Comparator.reverseOrder()))
                .toList();
        List<PostDTO> postDTOS = toPostDTOS(descLikeCountPosts);

        MvcResult result = mvc.perform(get("/api/posts?sortBy=LIKES"))
                .andExpect(status().isOk())
                .andReturn();

        GetPostsResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetPostsResponse.class);
        assertThat(response.posts()).isEqualTo(postDTOS);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 게시글 목록 조회 - 조회수")
    void getPosts_VIEWS() throws Exception {
        List<Post> posts = savePosts(member);
        List<Post> descViewCountPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getViewCount, Comparator.reverseOrder())
                        .thenComparing(Post::getId, Comparator.reverseOrder()))
                .toList();
        List<PostDTO> postDTOS = toPostDTOS(descViewCountPosts);

        MvcResult result = mvc.perform(get("/api/posts?sortBy=VIEWS"))
                .andExpect(status().isOk())
                .andReturn();

        GetPostsResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetPostsResponse.class);
        assertThat(response.posts()).isEqualTo(postDTOS);
    }

    @ParameterizedTest
    @WithMockCustomMember
    @MethodSource("getCreatePostParameter")
    @DisplayName("400 : 게시글을 생성 - 요청에 대한 Validation 예외")
    void createPost_Error_Validation(CreatePostRequest request) throws Exception {
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("400 : 게시글을 생성 - 자동 삭제 시간 오류 [현재 시간보다 이전 시간 입력]")
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

    private List<PostDTO> toPostDTOS(List<Post> descCreateAtPosts) {
        return descCreateAtPosts.stream()
                .map(post -> new PostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getViewCount(),
                        post.getLikeCount(),
                        post.isPrivate(),
                        post.getCreatedAt(),
                        post.getMember().getId(),
                        post.getMember().getNickname()
                ))
                .toList();
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

    private List<Post> savePosts(Member member) {
        return Stream.generate(() -> member)
                .limit(10)
                .map(m -> {
                    int randomLikeCount = ThreadLocalRandom.current().nextInt(0, 50);
                    int randomViewCount = ThreadLocalRandom.current().nextInt(0, 50);

                    return Post.builder()
                            .title("테스트제목" + randomLikeCount)
                            .content("테스트내용" + randomViewCount)
                            .isPrivate(false)
                            .isDeleted(false)
                            .likeCount(randomLikeCount)
                            .viewCount(randomViewCount)
                            .autoDeleted(null)
                            .member(m)
                            .build();
                })
                .map(postRepository::save)
                .toList(); // 리스트로 변환
    }
}
