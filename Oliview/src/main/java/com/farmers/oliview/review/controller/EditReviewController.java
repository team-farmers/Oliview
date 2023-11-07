package com.farmers.oliview.review.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.service.EditReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("review")
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
public class EditReviewController {
	
	private final EditReviewService service;
	
	
	@RequestMapping("insert")
	public String insert() {
		return "review/insert";
	}	
	
	/** 게시글 삭제
	 * @param reviewNo
	 * @return
	 */
	@GetMapping("{reviewNo:[0-9]+}/delete")
	public String deleteReview(@PathVariable("reviewNo") int reviewNo, RedirectAttributes ra,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {
		
		if(loginMember == null) {
			ra.addAttribute("message", "로그인 후 이용해 주세요");
			return "redirect:/member/login";
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("reviewNo", reviewNo);
		paramMap.put("memberNo", loginMember.getMemberNo());
		
		int result = service.deleteReview(paramMap);
		
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = "삭제 되었습니다.";
			path = "redirect:/review/";
		} else {
			message = "삭제 되지 않았습니다.";
			path = "redirect:/";
		}
		
		ra.addFlashAttribute("message", message);
		return path;
	}

}
