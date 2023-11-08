package com.farmers.oliview.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.myPage.service.MyPageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
public class MyPageController {
	
	
	private final MyPageService service;
	
	
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
	
	
	
	/* -------- 회원탈퇴  -------- */

	/** 회원탈퇴
	 * @param loginMember
	 * @param ra
	 * @param status
	 * @return
	 */
	@PostMapping("secession")
	public String secession(@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra, SessionStatus status) {
		
		int result = service.secession(loginMember);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			message = "회원탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.";
			
			// 기존 정보 로그아웃
			status.setComplete();
			path = "redirect:/";
			
		} else {
			message = "회원탈퇴가 실패하였습니다.";
			path = "redirect:secession";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	

}
