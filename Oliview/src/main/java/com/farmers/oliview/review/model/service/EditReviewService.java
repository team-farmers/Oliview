package com.farmers.oliview.review.model.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.farmers.oliview.review.model.dto.Review;

public interface EditReviewService {

	// 게시글 작성
	int insertReview(Review review, MultipartFile img) throws IllegalStateException, IOException;


	// 게시글 삭제
	int deleteReview(Map<String, Integer> paramMap);

	int updateReview(Review review, MultipartFile img) throws IllegalStateException, IOException;
}
