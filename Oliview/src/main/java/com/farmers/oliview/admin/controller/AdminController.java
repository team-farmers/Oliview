package com.farmers.oliview.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.admin.model.dto.Report;
import com.farmers.oliview.admin.model.service.AdminService;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("admin")
@SessionAttributes({ "loginMember" })
@RequiredArgsConstructor
public class AdminController {

	private final AdminService service;

	// 회원 조회
	@GetMapping("memberInfo")
	public String memberInfo(int memberNo, Model model, RedirectAttributes ra,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {

		Member memberInfo = service.memberInfo(memberNo);

		String path = null;

		if (memberInfo != null) {
			model.addAttribute("memberInfo", memberInfo);
			path = "/admin/memberInfo";
		} else {
			path = "redirect:memberList";
		}

		return path;

	}

	// 회원 목록 조회
	@GetMapping("memberList")
	public String memberList(Model model) {

		List<Member> memberList = service.memberList();

		model.addAttribute("memberList", memberList);

		return "admin/memberList";
	}

	// 관리자 권한 변경

	@PostMapping("changeAuthority")
	public String changeAuthority(int memberNo, String memberEmail, RedirectAttributes ra) {

		int result = service.changeAuthority(memberNo);

		if (result > 0) {
			ra.addFlashAttribute("message", "권한 변경을 성공했습니다.");
		} else {
			ra.addFlashAttribute("message", "권한 변경을 실패했습니다.");
		}

		return "redirect:memberInfo";
	}

	// 회원 복구

	@PostMapping("restoration")
	public String restoration(int memberNo, RedirectAttributes ra) {

		int result = service.restoration(memberNo);

		if (result > 0) {
			ra.addFlashAttribute("message", "회원 복구 처리가  되었습니다.");
		} else {
			ra.addFlashAttribute("message", "회원 복구 처리가  실패했습니다.");
		}

		return "redirect:memberList";
	}

	// 회원 탈퇴
	@PostMapping("quit")
	public String quit(int memberNo, String memberEmail, RedirectAttributes ra) {

		int result = service.quit(memberNo);

		if (result > 0) {
			ra.addFlashAttribute("message", "회원 탈퇴 처리가 되었습니다.");
		} else {
			ra.addFlashAttribute("message", "회원 탈퇴 처리가 실패했습니다.");
		}

		return "redirect:memberList";
	}

//	// 리뷰 게시판 신고하기 불러오기
//	@GetMapping("report")
//	public String insertReport()
//
//	// 리뷰 게시판 신고하기
//	@PostMapping("report")
//	public String insertReport(RedirectAttributes ra, Report report, Review review,
//			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {
//
//		report.setReviewNo(review.getReviewNo());
//
//		int reportNo = service.insertReport(review);
//
//		if (reportNo > 0) {
//			ra.addFlashAttribute("message", "게시글 신고가 완료되었습니다.");
//			return String.format("redirect:/review/%d", reportNo);
//		}
//
//		ra.addFlashAttribute("message", "게시글 신고가 실패되었습니다.");
//		return "redirect:report";
//
//	}

	@GetMapping("reviewList")
	public String selectALL( Model model, RedirectAttributes ra) {

		List<Review> reviewReportList = service.reviewReportList();

		model.addAttribute("reviewReportList", reviewReportList);

		return "admin/reviewList";
	}
	

	@PostMapping("delete")
	public String deleteReview(int reviewNo,RedirectAttributes ra) {
		
		int result = service.delete(reviewNo);

		if (result > 0) {
			ra.addFlashAttribute("message", "게시글이 삭제 되었습니다.");
		} else {
			ra.addFlashAttribute("message", "게시글이 삭제 되었습니다.");
		}
		
		return "redirect:reviewReportList";
	}
	
	@GetMapping("reviewReportInfo")
	public String reviewReportInfo(int reviewNo, Model model, RedirectAttributes ra) {

		Member reviewReportInfo = service.reviewReportInfo(reviewNo);

		String path = null;

		if (reviewReportInfo != null) {
			model.addAttribute("reviewReportInfo", reviewReportInfo);
			path = "admin/reviewReportInfo";
		} else {
			path = "redirect:reviewList";
		}

		return path;

	}
}