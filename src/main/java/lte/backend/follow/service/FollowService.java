package lte.backend.follow.service;

import lombok.RequiredArgsConstructor;
import lte.backend.follow.domain.Follow;
import lte.backend.follow.dto.response.GetFollowCountResponse;
import lte.backend.follow.dto.response.GetFolloweeListResponse;
import lte.backend.follow.dto.response.GetFolloweePostsResponse;
import lte.backend.follow.dto.response.GetFollowerListResponse;
import lte.backend.follow.exception.AlreadyFollowMemberException;
import lte.backend.follow.exception.NonFollowMemberException;
import lte.backend.follow.exception.SelfFollowException;
import lte.backend.follow.repository.FollowRepository;
import lte.backend.member.domain.Member;
import lte.backend.member.exception.MemberNotFoundException;
import lte.backend.member.repository.MemberRepository;
import lte.backend.post.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FollowService {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void followMember(Long followeeId, Long memberId) {
        validateSelfFollow(followeeId, memberId);
        validateExistsMember(followeeId);
        validateAlreadyFollowMember(followeeId, memberId);
        Follow follow = Follow.builder()
                .followee(new Member(followeeId))
                .follower(new Member(memberId))
                .build();

        followRepository.save(follow);
    }

    @Transactional
    public void unfollowMember(Long followeeId, Long memberId) {
        validateSelfFollow(followeeId, memberId);
        Follow follow = followRepository.findByFolloweeIdAndFollowerId(followeeId, memberId)
                .orElseThrow(NonFollowMemberException::new);

        followRepository.delete(follow);
    }

    public GetFollowerListResponse getFollowerList(Long memberId, Long findMemberId, Pageable pageable) {
        validateExistsMember(memberId);
        validateExistsMember(findMemberId);
        return GetFollowerListResponse.from(followRepository.findFollowerList(memberId, findMemberId, pageable));
    }

    public GetFolloweeListResponse getFolloweeList(Long memberId, Long findMemberId, Pageable pageable) {
        validateExistsMember(memberId);
        validateExistsMember(findMemberId);
        return GetFolloweeListResponse.from(followRepository.findFolloweeList(memberId, findMemberId, pageable));
    }

    public GetFolloweePostsResponse getFolloweePostList(Long memberId) {
        Pageable pageable = PageRequest.of(0, 10);
        return GetFolloweePostsResponse.from(postRepository.findFolloweePosts(memberId, pageable));
    }

    public GetFollowCountResponse getFollowCount(Long memberId) {
        Long followerCount = followRepository.getFollowerCount(memberId);
        Long followeeCount = followRepository.getFolloweeCount(memberId);
        return new GetFollowCountResponse(followerCount, followeeCount);
    }

    private void validateSelfFollow(Long followeeId, Long memberId) {
        if (followeeId.equals(memberId)) {
            throw new SelfFollowException();
        }
    }

    private void validateAlreadyFollowMember(Long followeeId, Long memberId) {
        if (followRepository.existsByFolloweeIdAndFollowerId(followeeId, memberId)) {
            throw new AlreadyFollowMemberException();
        }
    }

    private void validateExistsMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
    }
}
