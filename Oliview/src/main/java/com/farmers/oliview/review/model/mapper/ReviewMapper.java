package com.farmers.oliview.review.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.farmers.oliview.review.model.dto.Review;

@Mapper
public interface ReviewMapper {

	
	/** 전체 리뷰수 조회
	 * @return reviewCount
	 */
	int getReviewCount();
	
	
	/** 전체 리뷰 조회
	 * @param rowBounds
	 * @return reviewList
	 */
	List<Review> AllReview(RowBounds rowBounds);
	
	
	/** 검색 결과
	 * @param searchInput
	 * @return 리뷰 List
	 */
	List<Review> searchReview(String searchInput);
	
	
	/** 닉네임 검색 결과
	 * @param searchNick
	 * @return
	 */
	List<Review> searchReviewNick(String searchInput);

	/** 리뷰 상세 조회
	 * @param map
	 * @return review
	 */
	Review reviewDetail(Map<String, Object> map);

	
	/** 찜 체크
	 * @param map
	 * @return 
	 */
	int likeCheck(Map<String, Object> map);
	

	/** 찜
	 * @param paramMap
	 * @return
	 */
	int reviewLike(Map<String, Object> paramMap);

	
	/** 찜 테이블에서 행 삭제
	 * @param paramMap
	 * @return
	 */
	int deleteReviewLike(Map<String, Object> paramMap);

	
	/** 찜 테이블에서 행 삽입
	 * @param paramMap
	 * @return
	 */
	int insertReviewLike(Map<String, Object> paramMap);



	/** 조회수 증가
	 * @param reviewNo
	 * @return result
	 */
	int updateReadCount(int reviewNo);


	/** 다른 리뷰
	 * @param reviewTitle
	 * @return review
	 */
	List<Review> otherReview(String reviewTitle);












}
