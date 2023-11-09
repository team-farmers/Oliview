package com.farmers.oliview.review.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.mapper.EditReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class EditReviewServiceImpl implements EditReviewService {

	private final EditReviewMapper mapper;
	
	@Value("${my.board.location}")
	private String folderPath;
	
	@Value("${my.board.webpath}")
	private String webPath;

	// 게시글 작성
	@Override
	public int insertReview(Review review) {
		
		int result = mapper.insertReview(review);

		if (result == 0) return 0;
	 

		return result;
	}

	@Override
	public int updateReview(Review review, String deleteOrder) {
		return 0;
	}
	
	@Override
	public int deleteReview(Map<String, Integer> paramMap) {
		return mapper.deleteReview(paramMap);
	}

}
