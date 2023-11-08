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
	
	
	
	// ============================================================
	
	// 리뷰 상세 조회
	@Override
	public Review reviewDetail(int reviewNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// 다른 리뷰 가져오기
	@Override
	public List<Review> otherReview(String reviewTitle) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// 리뷰 찜 여부 확인
	@Override
	public int likeCheck(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	// 조회수 업데이트 처리
	@Override
	public int updateReadCount(int reviewNo) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	// 찜 처리
	@Override
	public int reviewLike(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	// ============================================================
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
