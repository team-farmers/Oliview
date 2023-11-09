package com.farmers.oliview.review.model.service;

import java.util.List;
import java.util.Map;

import com.farmers.oliview.review.model.dto.Review;

public interface ReviewService {

	/** 전체 조회
	 * @param cp
	 * @return reviews
	 */
	Map<String, Object> allReview(int cp);

	/** 검색
	 * @param searchInput
	 * @param cp
	 * @return review
	 */
	Map<String, Object> searchReview(String searchInput, int cp);

	/** 최신순 조회
	 * @return reviewList
	 */
	List<Review> searchLatest();

	
	//===========================================================================
	
	/** 리뷰 상세 조회
	 * @param map
	 * @return review
	 */
	Review reviewDetail(Map<String, Object> map);

	/** 다른 리뷰 조회
	 * @param reviewNo 
	 * @param reviewTitle
	 * @return reviewList
	 */
	List<Review> otherReview(Map<String, Object> map2);

	/** 리뷰 찜 여부 확인
	 * @param map
	 * @return result
	 */
	int likeCheck(Map<String, Object> map);

	/** 조회수 업데이트 처리
	 * @param reviewNo
	 * @return result
	 */
	int updateReadCount(int reviewNo);

	/** 찜 처리
	 * @param paramMap
	 * @return
	 */
	int reviewLike(Map<String, Object> paramMap);
	
	//===========================================================================



}
