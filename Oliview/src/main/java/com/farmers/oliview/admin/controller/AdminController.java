package com.farmers.oliview.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.admin.model.service.AdminService;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.dto.ReviewReport;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.dto.TogetherReport;

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
	@PostMapping("admin")
	public String admin(int memberNo,RedirectAttributes ra) {
		
		int result = service.admin(memberNo);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "권한을 관리자로 변경했습니다");
		} else {
			ra.addFlashAttribute("message", "권한을 관리자로 변경하지 못했습니다.");
		}
		
		return "redirect:memberList";
	}

	@PostMapping("user")
	public String user(int memberNo,RedirectAttributes ra) {

		int result = service.user(memberNo);

		if (result > 0) {
			ra.addFlashAttribute("message", "권한을 일반 사용자로 변경했습니다.");
		} else {
			ra.addFlashAttribute("message", "권한을 일반 사용자로 변경하지 못했습니다.");
		}

		return "redirect:memberList";
	}

	// 회원 탈퇴
	@PostMapping("quit")
	public String quit(int memberNo, RedirectAttributes ra) {
		
		int result = service.quit(memberNo);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "회원 탈퇴 처리가 되었습니다.");
		} else {
			ra.addFlashAttribute("message", "회원 탈퇴 처리가 실패했습니다.");
		}
		
		return "redirect:memberList";
	}
	
	// 회원 복구
	@PostMapping("restoration")
	public String restoration(int memberNo, RedirectAttributes ra) {

		int result = service.restoration(memberNo);

		if (result > 0) {
			ra.addFlashAttribute("message", "회원 복구 처리가 되었습니다.");
		} else {
			ra.addFlashAttribute("message", "회원 복구 처리가 실패했습니다.");
		}

		return "redirect:memberList";
	}

// ************************************************************************************************************

	@GetMapping("reviewList")
	public String selectALL( Model model, RedirectAttributes ra) {

		List<Review> reviewReportList = service.reviewReportList();

		model.addAttribute("reviewReportList", reviewReportList);

		return "admin/reviewList";
	}
	

	@PostMapping("reviewadmin")
	public String deleteReview(int reviewNo, RedirectAttributes ra) {
		
		int result = service.delete(reviewNo);

		if (result > 0) {
			ra.addFlashAttribute("message", "게시글 상태가 변경 되었습니다.");
		} else {
			ra.addFlashAttribute("message", "게시글 상태가 변경 되었습니다.");
		}
		
		return "redirect:reviewList";
	}
	
	


	@GetMapping("togetherList")
	public String togetherList(Model model, RedirectAttributes ra) {
		
		List<Together> togetherList = service.togetherList();

		model.addAttribute("togetherList", togetherList);
		
		return "admin/togetherList";
	}
	
	@PostMapping("boardadmin")
	public String deleteTogether(int boardNo, RedirectAttributes ra) {
		
		int result = service.deleteTogether(boardNo);

		if (result > 0) {
			ra.addFlashAttribute("message", "게시글 상태가 변경 되었습니다.");
		} else {
			ra.addFlashAttribute("message", "게시글 상태가 변경 되었습니다.");
		}
		
		return "redirect:togetherList";
	}
	
	@GetMapping("reviewReportList")
	public String reportlist(Model model, RedirectAttributes ra) {
		
		List<ReviewReport> reportlist = service.reportlist();

		model.addAttribute("reportlist", reportlist);
		
		return "admin/reviewReportList";
	}
	
	@GetMapping("togetherReportList")
	public String togetherReportList(Model model, RedirectAttributes ra) {
		
		List<TogetherReport> togetherReportList = service.togetherReportList();
		
		model.addAttribute("togetherReportList", togetherReportList);
		
		return "admin/togetherReportList";
	}
	
	@PostMapping("reviewAdminad")
	public String deleteReviewad(int reviewNo, RedirectAttributes ra) {
		
		int result = service.deleteReviewad(reviewNo);

		if (result > 0) {
			ra.addFlashAttribute("message", "게시글 상태가 변경 되었습니다.");
		} else {
			ra.addFlashAttribute("message", "게시글 상태가 변경 되었습니다.");
		}
		
		return "redirect:reviewReportList";
	}
	
	@PostMapping("boardDelete")
	public String boardDeleteAd(int boardNo, RedirectAttributes ra) {
		
		int result = service.boardDeleteAd(boardNo);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "게시글 상태가 변경 되었습니다.");
		} else {
			ra.addFlashAttribute("message", "게시글 상태가 변경 되었습니다.");
		}
		
		return "redirect:togetherReportList";
	}
	

	


		
	
	
}