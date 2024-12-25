package lte.backend.util;

import lte.backend.Integration.fixture.IntergrationFixture;
import lte.backend.member.repository.MemberRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("test")
public class InitDB {

    private final MemberRepository memberRepository;

    public InitDB(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDB() {
        memberRepository.save(IntergrationFixture.testMember);
    }
}
