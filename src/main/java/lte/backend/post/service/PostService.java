package lte.backend.post.service;

import lombok.RequiredArgsConstructor;
import lte.backend.follow.repository.FollowRepository;
import lte.backend.member.domain.Member;
import lte.backend.member.exception.MemberNotFoundException;
import lte.backend.member.repository.MemberRepository;
import lte.backend.notification.dto.FolloweeNewPostNotificationEvent;
import lte.backend.post.domain.Post;
import lte.backend.post.dto.PostDTO;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.request.GetPostsRequest;
import lte.backend.post.dto.request.UpdatePostRequest;
import lte.backend.post.dto.response.CreatePostResponse;
import lte.backend.post.dto.response.GetPostResponse;
import lte.backend.post.dto.response.GetPostsResponse;
import lte.backend.post.dto.response.UpdatePostResponse;
import lte.backend.post.exception.InvalidDeletedTimeBadRequestException;
import lte.backend.post.exception.PostNotFoundException;
import lte.backend.post.exception.PostPermissionException;
import lte.backend.post.exception.PrivatePostAccessException;
import lte.backend.post.repository.PostRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

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
        eventPublisher.publishEvent(new FolloweeNewPostNotificationEvent(post, member));

        return new CreatePostResponse(savedPost.getId());
    }

    @Transactional
    public UpdatePostResponse update(UpdatePostRequest request, Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        validatePostAuthor(memberId, post);
        validateAutoDeletedTimeNotFuture(request.autoDeleted());

        post.update(request.title(), request.content(), request.isPrivate(), request.autoDeleted());

        return new UpdatePostResponse(postId);
    }

    @Transactional
    public void delete(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        validatePostAuthor(memberId, post);

        postRepository.delete(post);
    }

    @Transactional
    public GetPostResponse getPost(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        validatePrivatePostAccess(memberId, post);
        post.incrementViewCount();

        return GetPostResponse.of(post, post.getMember());
    }

    @Transactional
    public void deleteExpiredAutoDeletedPosts() {
        postRepository.softDeleteExpiredAutoDeletedPosts(LocalDateTime.now(clock));
    }

    private void validatePrivatePostAccess(Long memberId, Post post) {
        if (!post.isPrivate()) return;

        Long authorId = post.getMember().getId();
        if (!authorId.equals(memberId) && !followRepository.existsByFolloweeIdAndFollowerId(memberId, authorId)) {
            throw new PrivatePostAccessException();
        }
    }

    public GetPostsResponse getPosts(Pageable pageable, GetPostsRequest request) {
        Slice<PostDTO> slicePosts = postRepository.getPosts(pageable, request.searchBy(), request.searchValue(), request.sortBy());
        return GetPostsResponse.from(slicePosts);
    }

    private void validatePostAuthor(Long memberId, Post post) {
        if (!memberId.equals(post.getMember().getId())) {
            throw new PostPermissionException();
        }
    }

    private void validateAutoDeletedTimeNotFuture(LocalDateTime autoDeletedTime) {
        if (autoDeletedTime != null && !autoDeletedTime.isAfter(LocalDateTime.now())) {
            throw new InvalidDeletedTimeBadRequestException("자동 삭제 시간을 다시 설정해주세요.");
        }
    }
}
