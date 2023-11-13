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
	public Map<String, Object> allReview(int cp, int sort) {
		
		// 인기순 1, 최신순 2, 평점순 3
		int reviewCount;
		if(sort == 3) {
			reviewCount = mapper.ratingCount();
		} else {
			// 인기, 최신 리뷰 수 조회
			reviewCount = mapper.getReviewCount();
		}
		
		
		// Pagination 객체 생성
		Pagination pagination = new Pagination(cp, reviewCount);
		
		
		// RowBounds 객체 생성
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();
		int limit = pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Review> reviewList = null;
		switch (sort) {
		case 1 : reviewList =  mapper.allReview(rowBounds); break;
		case 2 : reviewList = mapper.allSortLatest(rowBounds); break;
		case 3 : reviewList =  mapper.allSortRating(rowBounds); break;
			
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("reviewList", reviewList);
		map.put("pagination", pagination);
		
		return map;
	}
	
	
	
	// 검색 조회 
	@Override
	public Map<String, Object> searchReview(String searchInput, int cp, int sort) {
		
		
		// 인기순 1, 최신순 2, 평점순 3
		int reviewCount;
		if(sort == 3) {
			reviewCount = mapper.searchRatingCount(searchInput);
		} else {
			// 인기, 최신 리뷰 수 조회
			reviewCount = mapper.searchReviewCount(searchInput);
		}
		
		
		// Pagination 객체 생성
		Pagination pagination = new Pagination(cp, reviewCount);
		
		// RowBounds 객체 생성
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();
		int limit = pagination.getLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		List<Review> reviewList = null;
		switch (sort) {
		case 1 : reviewList =  mapper.searchReview(searchInput, rowBounds); break;
		case 2 : reviewList = mapper.searchSortLatest(searchInput, rowBounds); break;
		case 3 : reviewList =  mapper.searchSortRating(searchInput, rowBounds); break;
			
		}
	
		
		Map<String, Object> map = new HashMap<>();
		map.put("reviewList", reviewList);
		map.put("pagination", pagination);
		
		return map;
	}
	

	// 최신순
	@Override
	public Map<String, Object> sortLatest(String searchInput, int cp) {
		
		
		// 리뷰 수 조회
		int reviewCount = mapper.searchReviewCount(searchInput);
		
		// Pagination 객체 생성
		Pagination pagination = new Pagination(cp, reviewCount);
		
		// RowBounds 객체 생성
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();
		int limit = pagination.getLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Review> reviewList =  mapper.sortLatest(searchInput, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put("reviewList", reviewList);
		map.put("pagination", pagination);
		
		return map;
	}
	
	// 평점순
	@Override
	public List<Review> sortRating(String searchInput) {
		return mapper.sortRating(searchInput);
	}

	// 평점순 가게조회
	@Override
	public List<Review> ratingResult(String reviewTitle) {
		return mapper.ratingResult(reviewTitle);
	}

	
	// ============================================================
	
	// 리뷰 상세 조회
	@Override
	public Review reviewDetail(Map<String, Object> map) {
		return mapper.reviewDetail(map);
	}
	
	
	// 다른 리뷰 가져오기
	@Override
	public List<Review> otherReview(Map<String, Object> map2) {
		return mapper.otherReview(map2);
	}
	
	
	// 리뷰 찜 여부 확인
	@Override
	public int likeCheck(Map<String, Object> map) {
		return mapper.likeCheck(map);
	}
	
	
	// 조회수 업데이트 처리
	@Override
	public int updateReadCount(int reviewNo) {
		return mapper.updateReadCount(reviewNo);
	}
	
	
	// 찜 처리
	@Override
	public int reviewLike(Map<String, Object> paramMap) {
		
		int result = 0;
		
		// 찜 되어있으면 check == 1
		// 찜 해제 -> LIKE 테이블에서 DELETE
		if((Integer)(paramMap.get("check")) == 1) {
			result = mapper.deleteReviewLike(paramMap);
		}
		else { // 찜 안되어 있으면 check == 0
			// 찜 등록 -> LIKE 테이블에 INSERT
			result = mapper.insertReviewLike(paramMap);
		}
		
		if(result == 0) return -1;
		
		return result;
	}
	
	
	// ============================================================
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
