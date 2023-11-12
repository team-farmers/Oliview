package com.farmers.oliview.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farmers.oliview.main.model.mapper.MainMapper;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.together.dto.Together;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{
	
	private final MainMapper mapper;
	
	@Override
	public List<Review> selectReview() {
		return mapper.selectReview();
	}
	
	@Override
	public List<Together> selectTogether() {
		return mapper.selectTogether();
	}
	
	

}
