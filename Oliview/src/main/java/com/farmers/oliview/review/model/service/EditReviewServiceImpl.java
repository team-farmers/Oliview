package com.farmers.oliview.review.model.service;

import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.review.model.mapper.EditReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditReviewServiceImpl implements EditReviewService{
	
	private final EditReviewMapper mapper;
	
	@Override
	public int deleteReview(Map<String, Object> paramMap) {
		return mapper.deleteReview(paramMap);
	}
}
