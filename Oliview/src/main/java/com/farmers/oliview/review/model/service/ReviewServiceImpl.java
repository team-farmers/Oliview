package com.farmers.oliview.review.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	
	
	private final ReviewMapper mapper;
	
	
	// 리뷰 검색
	@Override
	public List<Review> searchReview(String searchInput) {
		
		return mapper.searchReview(searchInput);
	}
	
	
	// 리뷰 상세 조회
	@Override
	public Review reviewDetail(int reviewNo) {
		return mapper.reviewDetail(reviewNo);
	}
	
	
	// 찜
	@Override
	public int reviewLike(Map<String, Object> paramMap) {
		return mapper.reviewLike(paramMap);
	}

}
