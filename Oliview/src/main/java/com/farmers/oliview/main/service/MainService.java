package com.farmers.oliview.main.service;

import java.util.List;

import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.together.dto.Together;

public interface MainService {

	List<Review> selectReview();

	List<Together> selectTogether();

}
