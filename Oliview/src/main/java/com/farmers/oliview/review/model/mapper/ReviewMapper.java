package com.farmers.oliview.review.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.farmers.oliview.review.model.dto.Review;

@Mapper
public interface ReviewMapper {

	/** 전체 리뷰 수
	 * @return reviewCount
	 */
	int getReviewCount();

	/** 리뷰 조회
	 * @param rowBounds
	 * @return reviewList
	 */
	List<Review> allReview(RowBounds rowBounds);

	/** 검색 리뷰 수 조회
	 * @param searchInput
	 * @return reviewCount
	 */
	int searchReviewCount(String searchInput);

	/** 검색 리뷰 조회
	 * @param searchInput
	 * @param rowBounds
	 * @return reviewList
	 */
	List<Review> searchReview(String searchInput, RowBounds rowBounds);


	
	//=====================================================================
	
	
	/** 상세 리뷰 조회
	 * @param map
	 * @return review
	 */
	Review reviewDetail(Map<String, Object> map);

	/** 다른 리뷰 가져오기
	 * @param reviewNo 
	 * @param reviewTitle
	 * @return reviewList
	 */
	List<Review> otherReview(Map<String, Object> map2);

	/** 찜 여부 확인
	 * @param map
	 * @return result
	 */
	int likeCheck(Map<String, Object> map);

	/** 조회수 업데이트 처리
	 * @param reviewNo
	 * @return result
	 */
	int updateReadCount(int reviewNo);

	/** 찜 테이블에서 행 삭제
	 * @param paramMap
	 * @return result
	 */
	int deleteReviewLike(Map<String, Object> paramMap);

	/** 찜 테이블에 행 삽입
	 * @param paramMap
	 * @return result
	 */
	int insertReviewLike(Map<String, Object> paramMap);



	
	
	










}
