package lte.backend.auth.scheduler;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.service.AuthService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthScheduler {

    private final AuthService authService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void autoDeletePosts() {
        authService.removeExpiredTokens();
    }
}
