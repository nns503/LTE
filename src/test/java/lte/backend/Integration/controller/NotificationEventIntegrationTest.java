package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.comment.dto.request.CreateCommentRequest;
import lte.backend.comment.repository.CommentRepository;
import lte.backend.follow.domain.Follow;
import lte.backend.follow.repository.FollowRepository;
import lte.backend.member.domain.Member;
import lte.backend.notification.domain.Notification;
import lte.backend.notification.repository.NotificationRepository;
import lte.backend.post.domain.Post;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.response.CreatePostResponse;
import lte.backend.post.repository.PostRepository;
import lte.backend.util.IntegrationEventTest;
import lte.backend.util.JsonMvcResponseMapper;
import lte.backend.util.WithMockCustomMember;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static lte.backend.notification.domain.NotificationType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificationEventIntegrationTest extends IntegrationEventTest {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private final Member member = IntegrationFixture.testMember1;
    private Member member2;
    private Member member3;

    @BeforeEach
    void beforeEach() {
        if (member2 == null) {
            member2 = memberRepository.save(IntegrationFixture.testMember2);
        }
        if (member3 == null) {
            member3 = memberRepository.save(IntegrationFixture.testMember3);
        }
    }

    @AfterEach
    void afterEach() {
        notificationRepository.deleteAllInBatch();
        commentRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        followRepository.deleteAllInBatch();
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 팔로위가 새로운 포스트를 올려 알림 전송")
    void followeeNewPost() throws Exception {
        followRepository.save(Follow.builder().
                followee(member).
                follower(member2).
                build());
        followRepository.save(Follow.builder().
                followee(member).
                follower(member3).
                build());
        CreatePostRequest request = new CreatePostRequest(
                "테스트제목",
                "테스트내용",
                false,
                null
        );
        String body = objectMapper.writeValueAsString(request);

        MvcResult result = mvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();
        CreatePostResponse response = JsonMvcResponseMapper.parseJsonResponse(result, CreatePostResponse.class);
        List<Notification> notifications = notificationRepository.findAll();

        assertThat(notifications).hasSize(2);
        assertThat(notifications).extracting("notificationType").containsOnly(FOLLOWEE_NEW_POST);
        assertThat(notifications).extracting("relatedId").containsOnly(response.postId());
        assertThat(notifications).extracting("member.id").containsOnly(member2.getId(), member3.getId());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 게시글에 새로운 댓글이 달림")
    void newComment() throws Exception {
        Post post = postRepository.save(IntegrationFixture.testPost(member2));
        CreateCommentRequest request = new CreateCommentRequest("새로운 댓글");
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(post("/api/posts/" + post.getId() + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
        List<Notification> notifications = notificationRepository.findAll();

        assertThat(notifications).hasSize(1);
        assertThat(notifications).extracting("notificationType").containsOnly(NEW_COMMENT);
        assertThat(notifications).extracting("relatedId").containsOnly(post.getId());
        assertThat(notifications).extracting("member.id").containsOnly(member2.getId());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 새로운 팔로워가 생김")
    void newFollower() throws Exception {
        mvc.perform(post("/api/members/" + member2.getId() + "/follow"))
                .andExpect(status().isOk());

        List<Notification> notifications = notificationRepository.findAll();
        assertThat(notifications).hasSize(1);
        assertThat(notifications).extracting("notificationType").containsOnly(NEW_FOLLOWER);
        assertThat(notifications).extracting("relatedId").containsOnly(member.getId());
        assertThat(notifications).extracting("member.id").containsOnly(member2.getId());
    }
}
