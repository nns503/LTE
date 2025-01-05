package lte.backend.Integration.fixture;

import lte.backend.member.domain.Member;
import lte.backend.member.domain.MemberRole;
import lte.backend.post.domain.Post;

public class IntegrationFixture {

    public static final Member testMember = Member.builder()
            .id(1L)
            .username("test1234")
            .password("test1234!!")
            .nickname("나테스트")
            .profileUrl("default_url")
            .role(MemberRole.ROLE_USER)
            .isDeleted(false)
            .build();

    public static final Member testMember2 = Member.builder()
            .id(2L)
            .username("test5678")
            .password("test5678!!")
            .nickname("김테스트")
            .profileUrl("default_url")
            .role(MemberRole.ROLE_USER)
            .isDeleted(false)
            .build();

    public static Post testPost(Member member) {
        return Post.builder()
                .title("테스트제목")
                .content("테스트내용")
                .isPrivate(false)
                .isDeleted(false)
                .likeCount(0)
                .viewCount(0)
                .autoDeleted(null)
                .member(member)
                .build();
    }
}
