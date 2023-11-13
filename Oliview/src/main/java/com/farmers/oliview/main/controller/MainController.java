package com.farmers.oliview.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	
	/** 비로그인 상태에서 마이페이지/글쓰기 접근 시
	 * @param ra
	 * @return
	 */
	@GetMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		ra.addFlashAttribute("message", "로그인 후 이용해주세요");
		return "redirect:/";
	}
	
	
	/** 로그인 상태에서 회원가입/로그인 페이지 접근 시
	 * @param ra
	 * @return
	 * 
	 * 
	 */
	@GetMapping("logoutError")
	public String logoutError(RedirectAttributes ra) {
		ra.addFlashAttribute("message", "정상적이지 않은 접근입니다.");
		return "redirect:/";
	}
	
	
	
	
	/** 관리자권한 없는 경우 관리자페이지 접근 시
	 * @param ra
	 * @return
	 */
	@GetMapping("adminError")
	public String adminError(RedirectAttributes ra) {
		ra.addFlashAttribute("message", "정상적이지 않은 접근입니다.");
		return "redirect:/";
	}
	
	
	
	
	

}
