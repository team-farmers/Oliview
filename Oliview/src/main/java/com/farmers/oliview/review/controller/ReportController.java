package com.farmers.oliview.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.dto.ReviewReport;
import com.farmers.oliview.review.model.service.ReportService;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.dto.TogetherReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@SessionAttributes({ "loginMember" })
public class ReportController {

	private final ReportService service;

	@GetMapping("reviewReport")
	public String report(Model model, @RequestParam(required = false) int reviewNo,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {

		if (loginMember == null) {
			return "redirect:/member/login";
		}

		Review review = service.report(reviewNo);

		model.addAttribute("review", review);

		return "review/reviewReport";
	}

	// 리뷰 게시판 신고하기
	@PostMapping("reviewReport")
	public String reviewReport(RedirectAttributes ra, ReviewReport report,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {


		int reportNo = service.insertReport(report);

		if (reportNo > 0) {
			ra.addFlashAttribute("message", "게시글 신고가 완료되었습니다.");
			return String.format("redirect:/review/%d", report.getReviewNo());
		}

		ra.addFlashAttribute("message", "게시글 신고가 실패되었습니다.");
		return "redirect:/";

	}
	
	
	@GetMapping("togetherReport")
	public String togetherReport(Model model, @RequestParam(required = false) int boardNo,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {
		
		if (loginMember == null) {
			return "redirect:/member/login";
		}
		
		Together together = service.tReport(boardNo);
		
		model.addAttribute("together", together);
		
		return "together/togetherReport";
	}
	
//	// 리뷰 게시판 신고하기
//	@PostMapping("togetherReport")
//	public String togetherReport(RedirectAttributes ra, TogetherReport report, Model model,
//			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {
//		
//		
//		int reportNo = service.insertTReport(report);
//		
//		if (reportNo > 0) {
//			ra.addFlashAttribute("message", "게시글 신고가 완료되었습니다.");
//			return String.format("redirect:/review/%d", report.getBoardNo());
//		}
//		
//		ra.addFlashAttribute("message", "게시글 신고가 실패되었습니다.");
//		return "redirect:/";
//		
//	}

}
