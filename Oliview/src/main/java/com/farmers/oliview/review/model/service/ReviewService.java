package com.farmers.oliview.review.model.service;

import java.util.List;
import java.util.Map;

import com.farmers.oliview.review.model.dto.Review;

public interface ReviewService {

	/** 전체 조회
	 * @param cp
	 * @return reviews
	 */
	Map<String, Object> allReview(int cp);

	/** 검색
	 * @param searchInput
	 * @param cp
	 * @return review
	 */
	Map<String, Object> searchReview(String searchInput, int cp);

	/** 최신순 조회
	 * @return reviewList
	 */
	List<Review> searchLatest();




}
