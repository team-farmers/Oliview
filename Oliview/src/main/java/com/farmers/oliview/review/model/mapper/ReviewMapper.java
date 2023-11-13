package com.farmers.oliview.review.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.farmers.oliview.review.model.dto.Review;

/**
 * 
 */
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

	/** 평점순 가게수 조회
	 * @return
	 */
	int ratingCount();
	
	/** 최신순 
	 * @param rowBounds
	 */
	List<Review> allSortLatest(RowBounds rowBounds);

	/** 평점순
	 * @param rowBounds
	 */
	List<Review> allSortRating(RowBounds rowBounds);

	
	
	//=====================================================================
	
	
	/** 검색 리뷰 수 조회
	 * @param searchInput
	 * @return reviewCount
	 */
	int searchReviewCount(String searchInput);

	/** 검색 리뷰 수 조회(평점순)
	 * @param searchInput
	 * @return
	 */
	int searchRatingCount(String searchInput);

	/** 검색 리뷰 조회
	 * @param searchInput
	 * @param rowBounds
	 * @return reviewList
	 */
	List<Review> searchReview(String searchInput, RowBounds rowBounds);
	

	/** 검색 리뷰 조회 최신순
	 * @param searchInput 
	 * @param rowBounds
	 * @return
	 */
	List<Review> searchSortLatest(String searchInput, RowBounds rowBounds);

	/** 검색 리뷰 조회 평점순
	 * @param searchInput 
	 * @param rowBounds
	 * @return
	 */
	List<Review> searchSortRating(String searchInput, RowBounds rowBounds);
	

	//=====================================================================	
	
	/** 최신순
	 * @param searchInput
	 * @param rowBounds 
	 * @return
	 */
	List<Review> sortLatest(String searchInput, RowBounds rowBounds);

	/** 평점순
	 * @param searchInput
	 * @return
	 */
	List<Review> sortRating(String searchInput);
	

	/** 평점순 가게리스트 조회
	 * @param reviewTitle
	 * @return
	 */
	List<Review> ratingResult(String reviewTitle);
	
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
