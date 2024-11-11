package article1be.blame.repository;

import article1be.blame.entity.Blame;
import article1be.reply.entity.Reply;
import article1be.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BlameRepository extends JpaRepository<Blame, Long> {
    List<Blame> findByBlameBoardSeq(long boardSeq);

    List<Blame> findByBlameReplySeq(long replySeq);

    List<Blame> findByBlameReviewSeq(long reviewSeq);

    boolean existsByUserIdAndBoardId(long l, long l1);
}
