package lte.backend.post.scheduler;

import lombok.RequiredArgsConstructor;
import lte.backend.post.service.PostService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostScheduler {

    private final PostService postService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void autoDeletePosts() {
        postService.autoDeletePosts();
    }
}
