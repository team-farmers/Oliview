package com.farmers.oliview.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.admin.model.service.AdminService;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("admin")
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
public class AdminController {

	private final AdminService service;

	@GetMapping("report")
	public String report() {
		return "admin/report";
	}
	
	// 회원 정보 조회
	@GetMapping("{memberNo:[0-9]+}")
	public String memberInfo(@PathVariable("memberNo") int memberNo, Model model, RedirectAttributes ra,
			@SessionAttribute(value="loginMember", required=false) Member loginMember) {
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberNo", memberNo);
		
		Review memberInfo = service.memberInfo(map);
		
		String path = null;
		if(memberInfo != null) {
			model.addAttribute("memberInfo", memberInfo);
			path = "redirect:/admin/memberInfo";
		} else {
			path = "redirect/memberList";
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
	
	// 회원 정보 
	@GetMapping("memberInfo")
	public String memberInfo() {
		return "admin/memberInfo";
	}
	
	// 관리자 권한 변경
	@PostMapping("changeAuthority")
	public String changeAuthority(int memberNo, String memberEmail, RedirectAttributes ra) {
	
		int result = service.changeAuthority(memberNo);
		
		if(result > 0) {
			ra.addFlashAttribute("message", "권한 변경을 성공했습니다.");
		}else {
			ra.addFlashAttribute("message", "권한 변경을 실패했습니다.");
		}
		
		return "redirect:memberInfo";
	}
	
	// 회원 복구
	@PostMapping("restoration")
	public String restoration(
		int memberNo, String memberEmail, 
		RedirectAttributes ra) {
		
		int result = service.restoration(memberNo);
		
		if(result > 0) {
			ra.addFlashAttribute("message", "회원이 복구 되었습니다.");
		}else {
			ra.addFlashAttribute("message", "회원 복구를 실패했습니다.");
		}
		
		// selectMember?inputEmail=test06@kh.com
		return "redirect:memberList";
	}
	
	
	
	
}
