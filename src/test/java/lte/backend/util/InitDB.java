package lte.backend.util;

import lte.backend.member.domain.Member;
import lte.backend.member.domain.MemberRole;
import lte.backend.member.repository.MemberRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class InitDB {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public InitDB(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {
        Member member = Member.builder()
                .username("test1234")
                .password(passwordEncoder.encode("test1234!!"))
                .nickname("나테스트")
                .profileUrl("default_url")
                .role(MemberRole.ROLE_USER)
                .isDeleted(false)
                .build();
        memberRepository.save(member);
    }
}
