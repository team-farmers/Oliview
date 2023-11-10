package com.farmers.oliview.review.model.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.farmers.oliview.review.model.dto.Review;

public interface EditReviewService {

	int insertReview(Review review, MultipartFile img) throws IllegalStateException, IOException;

	int updateReview(Review review, MultipartFile img);

	int deleteReview(Map<String, Integer> paramMap);
}
