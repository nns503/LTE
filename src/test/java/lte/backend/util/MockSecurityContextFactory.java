package lte.backend.util;

import lte.backend.auth.domain.AuthMember;
import lte.backend.member.domain.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomMember> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomMember annotation) {
        Member member = Member.builder()
                .id(annotation.id())
                .username(annotation.username())
                .role(annotation.role())
                .profileUrl(annotation.profileUrl())
                .build();
        AuthMember authMember = new AuthMember(member);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authMember, null, authMember.getAuthorities());
        context.setAuthentication(authToken);

        return context;
    }
}
