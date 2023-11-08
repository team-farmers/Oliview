package com.farmers.oliview.review.controller;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.service.ReviewService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("review")
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService service;
	
	
	
	/** 리뷰 조회, 검색
	 * @param model
	 * @param searchInput
	 * @param cp
	 * @param paramMap
	 * @return
	 */
	@GetMapping("searchReview")
	public String searchReview(Model model,
			@RequestParam(required=false) String searchInput,
			@RequestParam(value="cp", required = false, defaultValue="1") int cp) {
		
	
		// 전체 조회
		if(searchInput == null) {
			Map<String, Object> map = service.allReview(cp);
			model.addAttribute("map",map);
		}
		// 검색
		else {
			Map<String, Object> map = service.searchReview(searchInput, cp);
			model.addAttribute("map",map);
		}
		
		return "review/result";
		
	}
	
	
	
	/** 최신순 조회
	 * @return
	 */
	@GetMapping(value="searchLatest", produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<Review> searchLatest(){
		
		return service.searchLatest();
	}
	
	
	

}
