package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.comment.domain.Comment;
import lte.backend.comment.dto.request.CreateCommentRequest;
import lte.backend.comment.dto.request.UpdateCommentRequest;
import lte.backend.comment.dto.response.GetCommentsResponse;
import lte.backend.comment.repository.CommentRepository;
import lte.backend.member.domain.Member;
import lte.backend.member.repository.MemberRepository;
import lte.backend.post.domain.Post;
import lte.backend.post.repository.PostRepository;
import lte.backend.util.IntegrationTest;
import lte.backend.util.JsonMvcResponseMapper;
import lte.backend.util.WithMockCustomMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentIntegrationTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private Member member1;
    private Member member2;
    private Post post1;

    @BeforeEach
    void setUp() {
        member1 = IntegrationFixture.testMember1;
        member2 = memberRepository.save(IntegrationFixture.testMember2);
        post1 = postRepository.save(IntegrationFixture.testPost(member1));
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 댓글 작성")
    void createComment() throws Exception {
        CreateCommentRequest request = new CreateCommentRequest("첫 댓글");
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(post("/api/posts/" + post1.getId() + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
        List<Comment> comments = commentRepository.findAll();

        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getContent()).isEqualTo(request.content());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 댓글 업데이트")
    void updateComment() throws Exception {
        Comment savedComment = commentRepository.save(IntegrationFixture.testComment1(member1, post1));
        UpdateCommentRequest request = new UpdateCommentRequest("바꾼 댓글");
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(put("/api/posts/" + post1.getId() + "/comments/" + savedComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getContent()).isEqualTo(request.content());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("403 : 댓글 업데이트 - 작성자가 아님")
    void updateComment_Not_Author() throws Exception {
        Comment savedComment = commentRepository.save(IntegrationFixture.testComment1(member2, post1));
        UpdateCommentRequest request = new UpdateCommentRequest("바꾼 댓글");
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(put("/api/posts/" + post1.getId() + "/comments/" + savedComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 댓글 삭제")
    void deleteComment() throws Exception {
        Comment savedComment = commentRepository.save(IntegrationFixture.testComment1(member1, post1));

        mvc.perform(delete("/api/posts/" + post1.getId() + "/comments/" + savedComment.getId()))
                .andExpect(status().isOk());

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(0);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("403 : 댓글 삭제 - 작성자가 아님")
    void deleteComment_Not_Author() throws Exception {
        Comment savedComment = commentRepository.save(IntegrationFixture.testComment1(member2, post1));

        mvc.perform(delete("/api/posts/" + post1.getId() + "/comments/" + savedComment.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 댓글 리스트 조회")
    void getComments() throws Exception {
        commentRepository.save(IntegrationFixture.testComment1(member1, post1));
        commentRepository.save(IntegrationFixture.testComment1(member1, post1));
        commentRepository.save(IntegrationFixture.testComment1(member2, post1));
        commentRepository.save(IntegrationFixture.testComment1(member2, post1));
        commentRepository.save(IntegrationFixture.testComment1(member2, post1));

        MvcResult result = mvc.perform(get("/api/posts/" + post1.getId() + "/comments"))
                .andExpect(status().isOk())
                .andReturn();

        GetCommentsResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetCommentsResponse.class);
        assertThat(response.sliceInfo().elements()).isEqualTo(5);
        assertThat(response.comments()).hasSize(5);
    }
}
