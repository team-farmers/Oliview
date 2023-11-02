package com.farmers.oliview.review.model.service;

import java.util.List;

import com.farmers.oliview.review.model.dto.Review;

public interface ReviewService {

	/** 검색 결과
	 * @param searchInput
	 * @return 리뷰 List
	 */
	List<Review> searchReview(String searchInput);

	/** 리뷰 상세 조회
	 * @param reviewNo
	 * @return review
	 */
	Review reviewDetail(int reviewNo);

}
