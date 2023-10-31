package com.farmers.oliview.review.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("review")
//@SessionAttributes
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService service;
	
	
	// 테스트
	@RequestMapping("result")
	public String result() {
		return "review/result";
	}
	
	// 리뷰 글쓰기
	
	// 리뷰 수정
	
	// 리뷰 삭제

	
	// 검색 결과 (검색어)
	@GetMapping("searchReview")
	public String searchReview(String searchInput, Model model) {
		
		List<Review> resultReview = service.searchReview(searchInput);
		
		model.addAttribute("resultReview",resultReview);
		return "result";
	}
	
	
	
	// 검색 결과 (닉네임 클릭 시 작성글)
	
	
	// 리뷰 상세 조회 -> reviewDetail 포워드
	
	
	// (인기순, 최신순, 평점순 -> js)
	// (NEXT 다음페이지 -> js)
	

}
