package lte.backend.auth.service;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.dto.request.JoinRequest;
import lte.backend.auth.exception.NicknameDuplicationException;
import lte.backend.auth.exception.UsernameDuplicationException;
import lte.backend.auth.repository.MemberRepository;
import lte.backend.member.domain.Member;
import lte.backend.member.domain.MemberRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(JoinRequest request) {
        String username = request.username();
        String nickname = request.nickname();

        checkDuplicateUsername(username);
        checkDuplicateNickname(nickname);

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .profileUrl(request.profileUrl())
                .role(MemberRole.ROLE_USER)
                .isDeleted(false)
                .build();

        memberRepository.save(member);
    }

    private void checkDuplicateNickname(String nickname) {
        if(memberRepository.existsByNickname(nickname)) {
            throw new NicknameDuplicationException();
        }
    }

    private void checkDuplicateUsername(String username) {
        if(memberRepository.existsByUsername(username)) {
            throw new UsernameDuplicationException();
        }
    }
}
