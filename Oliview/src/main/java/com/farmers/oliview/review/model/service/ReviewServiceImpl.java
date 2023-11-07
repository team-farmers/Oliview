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
	public Map<String, Object> AllReview(int cp) {
		
		// 전체 리뷰수 조회
		int reviewCount = mapper.getReviewCount();
		
		// cp, reviewCount 이용해서 Pagination 객체 생성
		Pagination pagination = new Pagination(cp, reviewCount);
		
		// RowBounds 객체 생성
		int offset = (pagination.getCurrentPage()-1) * pagination.getLimit();
		int limit = pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Review> reviewList = mapper.AllReview(rowBounds);
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("reviewList", reviewList);
		map.put("pagination", pagination);
		
		return map;
	}
	
	
	// 리뷰 검색
	@Override
	public List<Review> searchReview(String searchInput) {
		
		return mapper.searchReview(searchInput);
	}
	
	
	// 닉네임 리뷰 검색
	@Override
	public List<Review> searchReviewNick(String searchNick) {
		return mapper.searchReviewNick(searchNick);
	}
	
	
	// 리뷰 상세 조회
	@Override
	public Review reviewDetail(Map<String, Object> map) {
		return mapper.reviewDetail(map);
	}
	

	@Override
	public List<Review> otherReview(String reviewTitle) {
		return mapper.otherReview(reviewTitle);
	}
	
	
	// 찜 체크
	@Override
	public int likeCheck(Map<String, Object> map) {
		return mapper.likeCheck(map);
	}
	
	
	// 찜
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
	
	// 조회수 증가
	@Override
	public int updateReadCount(int reviewNo) {
		return mapper.updateReadCount(reviewNo);
	}
	

}
