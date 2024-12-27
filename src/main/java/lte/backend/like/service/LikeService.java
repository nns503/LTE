package lte.backend.like.service;

import lombok.RequiredArgsConstructor;
import lte.backend.like.domain.Like;
import lte.backend.like.exception.AlreadyLikedPostException;
import lte.backend.like.repository.LikeRepository;
import lte.backend.member.domain.Member;
import lte.backend.post.domain.Post;
import lte.backend.post.exception.PostNotFoundException;
import lte.backend.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public void likePost(Long postId, Long memberId) {
        validateAlreadyLikedPost(postId, memberId);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        likeRepository.save(Like.builder()
                .post(post)
                .member(new Member(memberId))
                .build());
        postRepository.incrementLikeCount(postId);
    }

    private void validateAlreadyLikedPost(Long postId, Long memberId) {
        if (likeRepository.existsByPostIdAndMemberId(postId, memberId)) {
            throw new AlreadyLikedPostException();
        }
    }
}
