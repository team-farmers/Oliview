package com.farmers.oliview.review.controller;

import java.io.Console;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.service.ReviewService;

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
	
	
	// 테스트
	@RequestMapping("result")
	public String result() {
		return "review/result";
	}

	
	// 검색 결과 (검색어)
	// (인기순, 최신순, 평점순 -> js)
	// (NEXT 다음페이지 -> js)
	@GetMapping("searchReview")
	public String searchReview(String searchInput, Model model) {
		
		List<Review> resultReview = service.searchReview(searchInput);
		
		model.addAttribute("resultReview",resultReview);
		
		return "review/result";
	}
	
	
	
	// 검색 결과 (닉네임 클릭 시 작성글)
	
	
	
	/** 리뷰 상세 조회 
	 * @param reviewNo
	 * @param model
	 * @param ra
	 * @return
	 */
	@GetMapping("{reviewNo:[0-9]+}")
	public String reviewDetail(@PathVariable("reviewNo") int reviewNo,
			Model model, RedirectAttributes ra,
			@SessionAttribute(value="loginMember", required=false) Member loginMember) {
		
		// 상세 조회 서비스 호출
		Map<String, Object> map = new HashMap<>();
		map.put("reviewNo", reviewNo);
		
		Review detailReview = service.reviewDetail(map);
		
		// 리턴 path
		String path = null;
		
		// 찜 하트
		if(detailReview != null) {
			
			// 조회 결과 review/reviewDetail로 포워드
			model.addAttribute("detailReview", detailReview);
			path = "review/reviewDetail";
			
			if(loginMember!=null) {
				map.put("memberNo", loginMember.getMemberNo());
				int likeCheck = service.likeCheck(map);
				
				if(likeCheck == 1) model.addAttribute("likeCheck", "on");
			}	
			
		}
		
		return path;

	}
	
	
	
	/** 찜
	 * @param paramMap
	 * @param loginMember
	 * @return
	 */
	@PostMapping("like")
	@ResponseBody
	public int like(@RequestBody Map<String, Object> paramMap,
			@SessionAttribute("loginMember") Member loginMember) {
		
		paramMap.put("memberNo",loginMember.getMemberNo());
		
		return service.reviewLike(paramMap);
	}
	
	
	
	
	// 신고하기 버튼 클릭시 이동
	
	
	
	
	


}
