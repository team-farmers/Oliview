package com.farmers.oliview.review.model.service;

import java.util.List;
import java.util.Map;

import com.farmers.oliview.review.model.dto.Review;


public interface ReviewService {


	
	/** 전체 조회
	 * @param cp
	 * @param sort 
	 * @return reviews
	 */
	Map<String, Object> allReview(int cp, int sort);

	/** 검색 조회
	 * @param searchInput
	 * @param cp
	 * @param sort 
	 * @return review
	 */
	Map<String, Object> searchReview(String searchInput, int cp, int sort);
	

	// ==============================================================================
	
	/** 최신순 조회 (비동기)
	 * @param searchInput
	 * @param cp 
	 * @return
	 */
	Map<String, Object> sortLatest(String searchInput, int cp);
	
	

	/** 평점순 조회 (비동기)
	 * @param searchInput
	 * @return
	 */
	Map<String, Object> sortRating(String searchInput, int cp);

	/** 평점순 가게 조회
	 * @param reviewTitle
	 * @return
	 */
	List<Review> ratingResult(String reviewTitle);

	
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
