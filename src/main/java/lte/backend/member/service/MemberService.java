package lte.backend.member.service;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.exception.NicknameDuplicationException;
import lte.backend.member.domain.Member;
import lte.backend.member.dto.request.UpdateNicknameRequest;
import lte.backend.member.dto.request.UpdatePasswordRequest;
import lte.backend.member.exception.InvalidPasswordException;
import lte.backend.member.exception.MemberNotFoundException;
import lte.backend.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updateNickname(UpdateNicknameRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        validateDuplicateMyNickname(request, member);
        validateDuplicateOtherNickname(request.nickname());

        member.updateNickname(request.nickname());
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        validateCurrentPassword(request, member);
        String encodedNewPassword = passwordEncoder.encode(request.newPassword());

        member.updatePassword(encodedNewPassword);
    }

    private void validateCurrentPassword(UpdatePasswordRequest request, Member member) {
        if (!passwordEncoder.matches(request.currentPassword(), member.getPassword())) {
            throw new InvalidPasswordException("현재 비밀번호와 다릅니다.");
        }
    }

    private void validateDuplicateMyNickname(UpdateNicknameRequest request, Member member) {
        if (member.getNickname().equals(request.nickname())) {
            throw new NicknameDuplicationException("현재 닉네임과 동일합니다.");
        }
    }

    private void validateDuplicateOtherNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new NicknameDuplicationException();
        }
    }
}
