package com.farmers.oliview.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.together.dto.Together;

@Mapper
public interface MainMapper {

	List<Review> selectReview();

	List<Together> selectTogether();
	

}
