package com.farmers.oliview.review.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.service.EditReviewService;
import com.farmers.oliview.review.model.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("editReview")
@SessionAttributes({"loginMember"})
public class EditReviewController {

	private final EditReviewService service;

	private final ReviewService ReviewService;

	// 게시글 작성 화면 전환
	@GetMapping("insert")
	public String insertReview(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			RedirectAttributes ra) {

		if (loginMember == null) {
			return "redirect:/member/login";
		}

		return "review/insert";

	}

	// 게시글 작성
	@PostMapping("insert")
	public String insertReview(RedirectAttributes ra, Review review, @RequestParam("inputImage") MultipartFile img,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember)
			throws IllegalStateException, IOException {

		review.setMemberNo(loginMember.getMemberNo());

		int reviewNo = service.insertReview(review, img);

		if (reviewNo > 0) {
			ra.addFlashAttribute("message", "게시글 작성 성공");
			return String.format("redirect:/review/%d", reviewNo);
		}

		ra.addFlashAttribute("message", "게시글 작성 실패");
		return "redirect:insert";
	}

	// 게시글 수정 화면 전환
	@GetMapping("/{reviewNo:[0-9]+}/update")
	public String updateReview(@PathVariable("reviewNo") int reviewNo, Model model) {

		Map<String, Object> map = new HashMap<>();
		map.put("reviewNo", reviewNo);

		Review review = ReviewService.reviewDetail(map);

		model.addAttribute("review", review);

		return "review/update";
	}

	// 게시글 수정
	@PostMapping("/{reviewNo:[0-9]+}/update")
	public String updateReview(@PathVariable("reviewNo") int reviewNo, Review review,
			@RequestParam("inputImg") MultipartFile img) throws IllegalStateException, IOException {

		review.setReviewNo(reviewNo);

		int result = service.updateReview(review, img);

		String message = null;
		String path = null;

		if (result > 0) {
			message = "게시글이 수정 되었습니다.";
			path = "redirect:/review/" + reviewNo;
		} else {
			message = "게시글 수정 실패...";
			path = "redirect:/review/{reviewNo}/update";
		}

		return path;
	}

	// 게시글 삭제
	@GetMapping("{reviewNo:[0-9]+}/delete")
	public String deleteReview(@PathVariable("reviewNo") int reviewNo, RedirectAttributes ra,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember ) {

		if (loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해주세요");
			return "redirect:/member/login";
		}

		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("reviewNo", reviewNo);
		paramMap.put("memberNo", loginMember.getMemberNo());

		int result = service.deleteReview(paramMap);

		String path = null;
		String message = null;

		if (result > 0) {
			message = "게시글을 삭제했습니다.";
			path = "redirect:/review/searchReview";
		} else {
			message = "게시글 삭제를 실패했습니다.";
			path = "redirect:/";
		}

		ra.addFlashAttribute("message", message);
		return path;
	}

}
