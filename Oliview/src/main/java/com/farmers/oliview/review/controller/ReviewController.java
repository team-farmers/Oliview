package com.farmers.oliview.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
	// 1. 로그인 버튼 -> login.html 포워드
	
	// 2. 회원가입 버튼 -> login.html 포워드
	
	// 3. 검색 결과 (검색어)
	
	// 4. 검색 결과 (닉네임 클릭 시 작성글)
	
	// 5. 리뷰 상세 조회 -> reviewDetail 포워드
	
	// (인기순, 최신순, 평점순 -> js)
	// (NEXT 다음페이지 -> js)
	

}
