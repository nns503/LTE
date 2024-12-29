package lte.backend.follow.service;

import lombok.RequiredArgsConstructor;
import lte.backend.follow.domain.Follow;
import lte.backend.follow.exception.AlreadyFollowMemberException;
import lte.backend.follow.exception.NonFollowMemberException;
import lte.backend.follow.exception.SelfFollowException;
import lte.backend.follow.repository.FollowRepository;
import lte.backend.member.domain.Member;
import lte.backend.member.exception.MemberNotFoundException;
import lte.backend.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void followMember(Long followeeId, Long memberId) {
        validateSelfFollow(followeeId, memberId);
        Member followee = memberRepository.findById(followeeId)
                .orElseThrow(MemberNotFoundException::new);
        validateAlreadyFollowMember(followee.getId(), memberId);
        Follow follow = Follow.builder()
                .followee(followee)
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
}
