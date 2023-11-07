package com.farmers.oliview.review.model.service;

import java.util.Map;

public interface EditReviewService {

	/** 게시글 삭제
	 * @param paramMap
	 * @return
	 */
	int deleteReview(Map<String, Object> paramMap);

}
