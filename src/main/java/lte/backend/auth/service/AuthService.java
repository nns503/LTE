package lte.backend.auth.service;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.auth.dto.request.JoinRequest;
import lte.backend.auth.exception.NicknameDuplicationException;
import lte.backend.auth.exception.UsernameDuplicationException;
import lte.backend.member.domain.Member;
import lte.backend.member.domain.MemberRole;
import lte.backend.member.exception.MemberNotFoundException;
import lte.backend.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(JoinRequest request) {
        String username = request.username();
        String nickname = request.nickname();

        validateDuplicateUsername(username);
        validateDuplicateNickname(nickname);

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);

        return new AuthMember(member);
    }

    private void validateDuplicateNickname(String nickname) {
        if(memberRepository.existsByNickname(nickname)) {
            throw new NicknameDuplicationException();
        }
    }

    private void validateDuplicateUsername(String username) {
        if(memberRepository.existsByUsername(username)) {
            throw new UsernameDuplicationException();
        }
    }
}
