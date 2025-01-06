package lte.backend.util;

import lte.backend.member.domain.MemberRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface WithMockCustomMember {
    long id() default 1L;

    String username() default "test1234";

    MemberRole role() default MemberRole.ROLE_USER;
}
