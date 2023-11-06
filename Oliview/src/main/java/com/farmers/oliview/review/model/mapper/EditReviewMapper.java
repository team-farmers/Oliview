package com.farmers.oliview.review.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EditReviewMapper {

	/** 게시글 삭제
	 * @param paramMap
	 * @return
	 */
	int deleteReview(Map<String, Object> paramMap);

}
