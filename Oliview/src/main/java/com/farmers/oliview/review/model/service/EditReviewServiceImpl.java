package com.farmers.oliview.review.model.service;

import org.springframework.stereotype.Service;

import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.mapper.EditReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditReviewServiceImpl implements EditReviewService {

	private final EditReviewMapper mapper;

	@Override
	public int insertReview(Review review) {
		int result = mapper.insertReview(review);

		if (result == 0)
			return 0;

		return result;
	}

	@Override
	public int updateReview(Review review, String deleteOrder) {
		return 0;
	}

}
