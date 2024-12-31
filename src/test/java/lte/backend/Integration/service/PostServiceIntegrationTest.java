package lte.backend.Integration.service;

import lte.backend.post.repository.PostRepository;
import lte.backend.post.service.PostService;
import lte.backend.util.IntegrationServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PostServiceIntegrationTest extends IntegrationServiceTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;

    @Test
    @DisplayName("조회수 동시성 테스트")
    void concurrencyViewCount() {

    }
}
