package com.farmers.oliview.review.model.service;

import java.util.Map;

import com.farmers.oliview.review.model.dto.Review;

public interface EditReviewService {

	/**
	 * 게시글 작성
	 * 
	 * @param review
	 * @return
	 */
	int insertReview(Review review);

	int updateReview(Review review, String deleteOrder);

}
