package lte.backend.post.repository;

import lte.backend.post.domain.PostSearchBy;
import lte.backend.post.domain.PostSortBy;
import lte.backend.post.dto.PostDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface GetPostRepository {
    Slice<PostDTO> getPosts(Pageable pageable, PostSearchBy searchBy, String searchValue, PostSortBy sortBy);
}
