package lte.backend.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lte.backend.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "MEMBERS")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(unique = true, nullable = false)
    private String nickname;

    private String profileUrl;

    @NotNull
    @Column(nullable = false)
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private MemberRole role;
}
