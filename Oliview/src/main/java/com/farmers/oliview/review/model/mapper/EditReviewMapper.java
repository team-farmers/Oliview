package com.farmers.oliview.review.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.review.model.dto.Review;

@Mapper
public interface EditReviewMapper {

	int insertReview(Review review);

	int deleteReview(Map<String, Integer> paramMap);

	int updateReview(Review review);




}
