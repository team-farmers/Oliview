package com.farmers.oliview.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("myPage")
public class MyPageController {
	
	
	/* ---------- 포워드 ----------- */
	
	// 프로필화면(마이페이지 메인)으로 포워드
	@GetMapping("profile")
	public String idFind() { 
		return "myPage/profile";
	}
	
	// 내가 쓴 글로 포워드
	@GetMapping("my-article")
	public String myArticle() { 
		return "myPage/my-article";
	}
	
	// 내가 쓴 댓글로 포워드
	@GetMapping("my-comment")
	public String myComment() { 
		return "myPage/my-comment";
	}
	
	// 내가 찜한 글로 포워드
	@GetMapping("choice-article")
	public String choiceArticle() { 
		return "myPage/choice-article";
	}
	
	// 회원 탈퇴 페이지로 포워드
	@GetMapping("secession")
	public String secession() { 
		return "myPage/secession";
	}
	 
	// 비밀번호 변경 페이지로 포워드
	@GetMapping("changePw")
	public String changePw() { 
		return "myPage/changePw";
	}
	
	
	

}
