package lte.backend.comment.service;

import lombok.RequiredArgsConstructor;
import lte.backend.comment.domain.Comment;
import lte.backend.comment.dto.CommentDTO;
import lte.backend.comment.dto.request.CreateCommentRequest;
import lte.backend.comment.dto.request.UpdateCommentRequest;
import lte.backend.comment.dto.response.GetCommentsResponse;
import lte.backend.comment.exception.CommentNotFoundException;
import lte.backend.comment.exception.CommentPermissionException;
import lte.backend.comment.repository.CommentRepository;
import lte.backend.member.domain.Member;
import lte.backend.member.exception.MemberNotFoundException;
import lte.backend.member.repository.MemberRepository;
import lte.backend.notification.dto.NewCommentNotificationEvent;
import lte.backend.post.domain.Post;
import lte.backend.post.exception.PostNotFoundException;
import lte.backend.post.repository.PostRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final ApplicationEventPublisher eventPublisher;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(CreateCommentRequest request, Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Comment comment = Comment.builder()
                .content(request.content())
                .member(member)
                .post(post)
                .build();

        commentRepository.save(comment);
        if (!memberId.equals(post.getMember().getId())) {
            eventPublisher.publishEvent(new NewCommentNotificationEvent(post, post.getMember()));
        }
    }

    @Transactional
    public void update(UpdateCommentRequest request, Long memberId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        validateCommentAuthor(comment.getMember().getId(), memberId);
        comment.updateContent(request.content());
    }

    @Transactional
    public void delete(Long memberId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        validateCommentAuthor(comment.getMember().getId(), memberId);
        commentRepository.delete(comment);
    }

    public GetCommentsResponse getComments(Long postId, Pageable pageable) {
        validateExistsPost(postId);
        Slice<CommentDTO> comments = commentRepository.getComments(postId, pageable);
        return GetCommentsResponse.from(comments);
    }

    private void validateCommentAuthor(Long authorId, Long memberId) {
        if (!authorId.equals(memberId)) {
            throw new CommentPermissionException();
        }
    }

    private void validateExistsPost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
    }
}
