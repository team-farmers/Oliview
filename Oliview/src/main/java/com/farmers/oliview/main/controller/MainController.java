package com.farmers.oliview.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.farmers.oliview.main.service.MainService;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.together.dto.Together;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
	
	
	private final MainService service;
	
	
	/** 메인페이지 포워드
	 * @return
	 */
	@RequestMapping("/")
	public String mainPage(Model model) {
		
		// 메인페이지에 리뷰 3개 얻어오기
		List<Review> reviewList = service.selectReview();
		
		// 메인페이지에 같이먹어오 3개 얻어오기
		List<Together> togetherList = service.selectTogether();
		
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("togetherList", togetherList);
		
		return "common/main";
	}
	
	
	
	

}
