package lte.backend.util;

import lte.backend.auth.domain.AuthMember;
import lte.backend.member.domain.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        Member member = Member.builder()
                .id(annotation.id())
                .username(annotation.username())
                .role(annotation.role())
                .build();
        AuthMember authMember = new AuthMember(member);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authMember, null, authMember.getAuthorities());
        context.setAuthentication(authToken);

        return context;
    }
}
