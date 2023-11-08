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

	/** 최신순 조회
	 * @return reviewList
	 */
	List<Review> searchLatest();

	










}
