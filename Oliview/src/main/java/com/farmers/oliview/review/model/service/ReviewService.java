package com.farmers.oliview.review.model.service;

import java.util.List;
import java.util.Map;

import com.farmers.oliview.review.model.dto.Review;

public interface ReviewService {

	
	/** 검색 x 전체 조회
	 * @param cp
	 * @return
	 */
	Map<String, Object> AllReview(int cp);
	
	
	/** 검색 결과
	 * @param searchInput
	 * @return 리뷰 List
	 */
	List<Review> searchReview(String searchInput);

	/** 닉네임 클릭시 검색 결과
	 * @param searchNick
	 * @return 
	 */
	List<Review> searchReviewNick(String searchInput);

	
	/** 리뷰 상세 조회
	 * @param map
	 * @return review
	 */
	Review reviewDetail(Map<String, Object> map);


	/** 찜 여부 확인
	 * @param map
	 * @return
	 */
	int likeCheck(Map<String, Object> map);

	/** 찜
	 * @param paramMap
	 * @return
	 */
	int reviewLike(Map<String, Object> paramMap);

	/** 조회수 증가
	 * @param reviewNo
	 * @return result
	 */
	int updateReadCount(int reviewNo);

	/** 다른 리뷰 조회
	 * @param reviewTitle
	 * @return review
	 */
	List<Review> otherReview(String reviewTitle);



}
