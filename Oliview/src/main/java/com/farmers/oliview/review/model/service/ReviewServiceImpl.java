package com.farmers.oliview.review.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	
	
	private final ReviewMapper mapper;
	
	@Override
	public List<Review> searchReview(String searchInput) {
		
		return mapper.searchReview(searchInput);
	}

}
