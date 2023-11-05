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
	public Review reviewDetail(Map<String, Object> map) {
		return mapper.reviewDetail(map);
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
		
		return mapper.countReviewLike((Integer)(paramMap.get("boardNo")));

	}
	
	

}
