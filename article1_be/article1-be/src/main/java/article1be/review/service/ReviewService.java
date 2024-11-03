package article1be.review.service;


import article1be.review.aggregate.entity.Review;
import article1be.review.dto.ReviewDTO;
import article1be.review.repository.ReviewRepository;
import article1be.common.exception.CustomException;
import article1be.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDto) {
        Review review = new Review(
                reviewDto.getUserSeq(),
                reviewDto.getSelectSeq(),
                reviewDto.getReviewContent(),
                reviewDto.getReviewWeather(),
                reviewDto.getReviewLocation(),
                reviewDto.getReviewBlind(),
                reviewDto.getReviewLikeYn(),
                Optional.ofNullable(reviewDto.getReviewReport()).orElse(0)
        );

        review = reviewRepository.save(review);
        log.info(" 리뷰가 성공적으로 생성되었습니다: {}", review.getReviewSeq());

        return new ReviewDTO(
                review.getReviewSeq(),
                review.getUserSeq(),
                review.getSelectSeq(),
                review.getReviewContent(),
                review.getReviewWeather(),
                review.getReviewLocation(),
                review.getReviewBlind(),
                review.getReviewLikeYn(),
                review.getReviewReport()
        );
    }

    public ReviewDTO getReviewById(Long reviewSeq) {
        Review review = reviewRepository.findById(reviewSeq)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        log.info(" 리뷰 조회 완료: {}", reviewSeq);

        return new ReviewDTO(
                review.getReviewSeq(),
                review.getUserSeq(),
                review.getSelectSeq(),
                review.getReviewContent(),
                review.getReviewWeather(),
                review.getReviewLocation(),
                review.getReviewBlind(),
                review.getReviewLikeYn(),
                review.getReviewReport()
        );
    }

    public List<ReviewDTO> getReviewsByUser(Long userSeq) {
        List<Review> reviews = reviewRepository.findByUserSeq(userSeq);
        log.info(" {}의 리뷰 조회 완료: {} ", userSeq, reviews.size());

        return reviews.stream().map(review -> new ReviewDTO(
                review.getReviewSeq(),
                review.getUserSeq(),
                review.getSelectSeq(),
                review.getReviewContent(),
                review.getReviewWeather(),
                review.getReviewLocation(),
                review.getReviewBlind(),
                review.getReviewLikeYn(),
                review.getReviewReport()
        )).collect(Collectors.toList());
    }


    @Transactional
    public ReviewDTO updateReview(Long reviewSeq, ReviewDTO reviewDto) {
        Review review = reviewRepository.findById(reviewSeq)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        review.updateReview(
                reviewDto.getReviewContent(),
                reviewDto.getReviewWeather(),
                reviewDto.getReviewLocation(),
                reviewDto.getReviewBlind(),
                reviewDto.getReviewLikeYn()
        );

        reviewRepository.save(review);
        log.info("리뷰가 성공적으로 업데이트되었습니다: {}", reviewSeq);

        return new ReviewDTO(
                review.getReviewSeq(),
                review.getUserSeq(),
                review.getSelectSeq(),
                review.getReviewContent(),
                review.getReviewWeather(),
                review.getReviewLocation(),
                review.getReviewBlind(),
                review.getReviewLikeYn(),
                review.getReviewReport()
        );
    }

    @Transactional
    public void deleteReview(Long reviewSeq) {
        Review review = reviewRepository.findById(reviewSeq)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        reviewRepository.delete(review);
        log.info("리뷰가 성공적으로 삭제되었습니다: {}", reviewSeq);
    }
}