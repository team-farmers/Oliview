package com.farmers.oliview.review.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.review.model.dto.Review;

@Mapper
public interface ReviewMapper {

	/** 검색 결과
	 * @param searchInput
	 * @return 리뷰 List
	 */
	List<Review> searchReview(String searchInput);

}
