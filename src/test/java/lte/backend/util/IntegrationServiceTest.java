package lte.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lte.backend.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class IntegrationServiceTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected MemberRepository memberRepository;
}
