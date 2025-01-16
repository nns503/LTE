package lte.backend.Integration.controller;

import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.member.domain.Member;
import lte.backend.notification.domain.Notification;
import lte.backend.notification.domain.NotificationType;
import lte.backend.notification.dto.GetNotificationDTO;
import lte.backend.notification.dto.request.SelectedNotificationDeleteRequest;
import lte.backend.notification.dto.response.GetNotificationResponse;
import lte.backend.notification.repository.NotificationRepository;
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

public class NotificationIntegrationTest extends IntegrationTest {

    @Autowired
    private NotificationRepository notificationRepository;

    private Member member;

    @BeforeEach
    void setup() {
        member = IntegrationFixture.testMember1;
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 알림 목록 조회")
    void getNotifications() throws Exception {
        Notification notification = saveNewPostNotification();
        Notification notification2 = saveNewPostNotification();

        MvcResult result = mvc.perform(get("/api/notification"))
                .andExpect(status().isOk())
                .andReturn();
        GetNotificationResponse response = JsonMvcResponseMapper.parseJsonResponse(result, GetNotificationResponse.class);

        assertThat(response.notifications()).hasSize(2);
        assertThat(response.notifications()).contains(GetNotificationDTO.from(notification), GetNotificationDTO.from(notification2));
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 알림 읽기 표시")
    void readNotification() throws Exception {
        Notification notification = saveNewPostNotification();
        Notification notification2 = saveNewPostNotification();

        mvc.perform(put("/api/notification/" + notification.getId()))
                .andExpect(status().isOk());

        assertThat(notificationRepository.findById(notification.getId())
                .orElseThrow(AssertionError::new).isRead()).isEqualTo(true);
        assertThat(notificationRepository.findById(notification2.getId())
                .orElseThrow(AssertionError::new).isRead()).isEqualTo(false);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 모든 알림 읽기 표시")
    void readNotifications() throws Exception {
        Notification notification = saveNewPostNotification();
        Notification notification2 = saveNewPostNotification();

        mvc.perform(put("/api/notification"))
                .andExpect(status().isOk());

        assertThat(notificationRepository.findById(notification.getId())
                .orElseThrow(AssertionError::new).isRead()).isEqualTo(true);
        assertThat(notificationRepository.findById(notification2.getId())
                .orElseThrow(AssertionError::new).isRead()).isEqualTo(true);
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 선택된 알림들 삭제")
    void selectedNotificationDelete() throws Exception {
        Notification notification = saveNewPostNotification();
        Notification notification2 = saveNewPostNotification();
        Notification notification3 = saveNewPostNotification();
        assertThat(notificationRepository.findAll().size()).isEqualTo(3);
        SelectedNotificationDeleteRequest request = new SelectedNotificationDeleteRequest(List.of(notification.getId(), notification3.getId()));
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(delete("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        assertThat(notificationRepository.findAll().size()).isEqualTo(1);
        assertThat(notificationRepository.findById(notification.getId())).isEmpty();
        assertThat(notificationRepository.findById(notification2.getId())).contains(notification2);
        assertThat(notificationRepository.findById(notification3.getId())).isEmpty();
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 읽은 알림들 삭제")
    void readNotificationsDelete() throws Exception {
        Notification notification = saveNewPostNotificationReadTrue();
        Notification notification2 = saveNewPostNotification();
        Notification notification3 = saveNewPostNotificationReadTrue();
        assertThat(notificationRepository.findAll().size()).isEqualTo(3);

        mvc.perform(delete("/api/notification/read"))
                .andExpect(status().isOk());

        assertThat(notificationRepository.findAll().size()).isEqualTo(1);
        assertThat(notificationRepository.findById(notification.getId())).isEmpty();
        assertThat(notificationRepository.findById(notification2.getId())).contains(notification2);
        assertThat(notificationRepository.findById(notification3.getId())).isEmpty();
    }

    private Notification saveNewPostNotification() {
        return notificationRepository.save(Notification.builder()
                .content(NotificationType.FOLLOWEE_NEW_POST.getMessage("테스트유저"))
                .notificationType(NotificationType.FOLLOWEE_NEW_POST)
                .relatedId(2L)
                .isRead(false)
                .member(member)
                .build());
    }

    private Notification saveNewPostNotificationReadTrue() {
        return notificationRepository.save(Notification.builder()
                .content(NotificationType.FOLLOWEE_NEW_POST.getMessage("테스트유저"))
                .notificationType(NotificationType.FOLLOWEE_NEW_POST)
                .relatedId(2L)
                .isRead(true)
                .member(member)
                .build());
    }
}
