package com.farmers.oliview.review.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.farmers.oliview.review.model.dto.Review;

public interface EditReviewService {

	int deleteReview(Map<String, Integer> paramMap);

	int insertReview(Review review);

	int updateReview(Review review, String deleteOrder);

}
