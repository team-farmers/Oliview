package com.farmers.oliview.review.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.review.model.dto.Pagination;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	
	
	private final ReviewMapper mapper;
	
	
	// 전체 조회
	@Override
	public Map<String, Object> allReview(int cp) {
		
		// 전체 리뷰 수 조회
		int reviewCount = mapper.getReviewCount();
		
		// Pagination 객체 생성
		Pagination pagination = new Pagination(cp, reviewCount);
		
		
		// RowBounds 객체 생성
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();
		int limit = pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Review> reviewList = mapper.allReview(rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put("reviewList", reviewList);
		map.put("pagination", pagination);
		
		
		return map;
	}
	
	
	// 검색 조회
	@Override
	public Map<String, Object> searchReview(String searchInput, int cp) {
		
		
		int reviewCount = mapper.searchReviewCount(searchInput);
		
		Pagination pagination = new Pagination(cp, reviewCount);
		
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();
		int limit = pagination.getLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Review> reviewList = mapper.searchReview(searchInput, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put("reviewList", reviewList);
		map.put("pagination", pagination);
		
		return map;
	}
	

	// 최신순 조회
	@Override
	public List<Review> searchLatest() {
		return mapper.searchLatest();
	}
}
