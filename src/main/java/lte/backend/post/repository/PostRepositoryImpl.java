package lte.backend.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lte.backend.post.domain.PostSearchBy;
import lte.backend.post.domain.PostSortBy;
import lte.backend.post.dto.PostDTO;
import lte.backend.post.dto.QPostDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static lte.backend.member.domain.QMember.member;
import static lte.backend.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements GetPostRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PostDTO> getPosts(Pageable pageable, PostSearchBy searchBy, String searchValue, PostSortBy sortBy) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(searchCondition(searchBy, searchValue));

        List<PostDTO> query = queryFactory
                .select(new QPostDTO(
                        post.id,
                        post.title,
                        post.viewCount,
                        post.likeCount,
                        post.isPrivate,
                        post.createdAt,
                        member.id,
                        member.nickname
                ))
                .from(post)
                .join(post.member, member)
                .where(builder)
                .orderBy(getOrderBy(sortBy), post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;

        if (query.size() > pageable.getPageSize()) {
            hasNext = true;
            query.remove(query.size() - 1);
        }

        return new SliceImpl<>(query, pageable, hasNext);
    }

    private BooleanExpression searchCondition(PostSearchBy searchBy, String searchValue) {
        if (searchValue == null || searchBy == null) {
            return null;
        }

        return switch (searchBy) {
            case TITLE -> post.title.likeIgnoreCase("%" + searchValue + "%");
            case NICKNAME -> member.nickname.eq(searchValue);
            default -> null;
        };
    }

    private OrderSpecifier<?> getOrderBy(PostSortBy sortBy) {
        if (sortBy == null) {
            sortBy = PostSortBy.DATE;
        }

        return switch (sortBy) {
            case LIKES -> post.likeCount.desc();
            case VIEWS -> post.viewCount.desc();
            default -> post.createdAt.desc();
        };
    }
}
