package com.farmers.oliview.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class MemberController {
	
	/*--------- 로그인 ---------*/
	
	// 로그인 페이지로 포워드
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	
	/*--------- 회원가입 ---------*/
	
	// 회원가입 메인 페이지로 포워드
	@GetMapping("signup")
	public String signup() {
		return "member/signup";
	}
	
	
	// 회원가입 약관동의 후 상세정보입력 페이지로 포워드
	@GetMapping("signup-agree")
	public String signupAgree() { // 동의정보 체크해서 저장해놔야함
		return "member/signup-info";
	}

	
	// 회원가입 정보입력 후 완료페이지로 포워드
	@PostMapping("signup-fin")
	public String signupFin() { // 개인정보 DB입력
		return "member/signup-fin";
	}
	
	
	
	

}
