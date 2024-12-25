package lte.backend.post.service;

import lombok.RequiredArgsConstructor;
import lte.backend.member.domain.Member;
import lte.backend.member.exception.MemberNotFoundException;
import lte.backend.member.repository.MemberRepository;
import lte.backend.post.domain.Post;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.request.UpdatePostRequest;
import lte.backend.post.dto.response.CreatePostResponse;
import lte.backend.post.dto.response.GetPostResponse;
import lte.backend.post.dto.response.UpdatePostResponse;
import lte.backend.post.exception.ForbiddenModificationException;
import lte.backend.post.exception.InvalidDeletedTimeBadRequestException;
import lte.backend.post.exception.PostNotFoundException;
import lte.backend.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CreatePostResponse create(CreatePostRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        validateAutoDeletedTimeNotFuture(request.autoDeleted());

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .viewCount(0)
                .likeCount(0)
                .isDeleted(false)
                .isPrivate(request.isPrivate())
                .autoDeleted(request.autoDeleted())
                .member(member)
                .build();
        Post savedPost = postRepository.save(post);

        return new CreatePostResponse(savedPost.getId());
    }

    @Transactional
    public UpdatePostResponse update(UpdatePostRequest request, Long memberId, Long postId) {
        Post post = postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(PostNotFoundException::new);

        validatePostAuthor(memberId, post);
        validateAutoDeletedTimeNotFuture(request.autoDeleted());

        post.update(request.title(), request.content(), request.isPrivate(), request.autoDeleted());

        return new UpdatePostResponse(postId);
    }

    @Transactional
    public void delete(Long memberId, Long postId) {
        Post post = postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(PostNotFoundException::new);
        validatePostAuthor(memberId, post);

        postRepository.delete(post);
    }

    public GetPostResponse getPost(Long postId) {
        Post post = postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(PostNotFoundException::new);

        // 이웃 보기 기능
        post.incrementViewCount();

        return GetPostResponse.of(post, post.getMember());
    }

    private void validatePostAuthor(Long memberId, Post post) {
        if (!memberId.equals(post.getMember().getId())) {
            throw new ForbiddenModificationException();
        }
    }

    private void validateAutoDeletedTimeNotFuture(LocalDateTime autoDeletedTime) {
        if (autoDeletedTime != null && !autoDeletedTime.isAfter(LocalDateTime.now())) {
            throw new InvalidDeletedTimeBadRequestException("자동 삭제 시간을 다시 설정해주세요.");
        }
    }
}
