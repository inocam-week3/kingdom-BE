package sparta.kingdombe.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.kingdombe.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
