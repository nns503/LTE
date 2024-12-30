package lte.backend.comment.repository;

import lte.backend.comment.domain.Comment;
import lte.backend.comment.dto.CommentDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new lte.backend.comment.dto.CommentDTO(c.id, c.content, c.createdAt, c.member.id, c.member.nickname) " +
            "FROM Comment c " +
            "WHERE c.post.id = :postId " +
            "ORDER BY c.createdAt DESC")
    Slice<CommentDTO> getComments(Long postId, Pageable pageable);
}
