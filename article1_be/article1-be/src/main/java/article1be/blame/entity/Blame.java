package article1be.blame.entity;

import article1be.common.aggregate.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "BLAME")
@Getter
public class Blame extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long blameSeq;                          // 신고 번호
    long blameUserSeq;                      // 신고자
    Long blameBoardSeq;                     // 신고 게시글 번호
    Long blameReplySeq;                     // 신고 댓글 번호
    Long blameReviewSeq;                    // 신고 리뷰 번호
    LocalDateTime blameProcessingDate;      // 신고 처리 날짜시간
    boolean blameStatus;                    // 처리 상태

    // 기본 생성자 추가
    public Blame() {
        // 기본 생성자
    }

    @Builder
    public Blame(
            long blameSeq,
            long blameUserSeq,
            Long blameBoardSeq,
            Long blameReplySeq,
            Long blameReviewSeq
    ) {
        this.blameSeq = blameSeq; // 여기에서 파라미터로 받은 값으로 설정
        this.blameUserSeq = blameUserSeq;
        this.blameBoardSeq = blameBoardSeq;
        this.blameReplySeq = blameReplySeq;
        this.blameReviewSeq = blameReviewSeq;
    }
}