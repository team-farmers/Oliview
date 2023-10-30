package com.farmers.oliview.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("member")
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
public class MemberController {
	
	private final MemberService service;
	
	
	
	/*--------- 로그인 ---------*/
	
	// 로그인 페이지로 포워드
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	
	// 로그인 서비스 수행
	/** 로그인 서비스
	 * @param inputMember
	 * @param model
	 * @param ra
	 * @return loginMember
	 */
	@PostMapping("login")
	public String login(Member inputMember, Model model, RedirectAttributes ra) {
		
		// 로그인 서비스 호출
		Member loginMember = service.login(inputMember);
		
		
		// 로그인 정보 일치 시
		if(loginMember != null) {
			
			// 회원정보 세션에 저장
			model.addAttribute("loginMember", loginMember);
			
			// 로그인 정보 저장(쿠키)
			
			// 메인페이지 리다이렉트
			return "redirect:/";
		} 
		
		// 로그인 정보 불일치시
		if(loginMember == null) {
			ra.addFlashAttribute("message", "회원정보가 일치하지 않습니다.");
		}
		
		// 로그인페이지 리다이렉트
		return "redirect:login";
	}
	
	
	
	/*--------- 로그아웃 ---------*/
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
	
	
	
	
	/*--------- 회원가입 (forward)---------*/
	
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
	
	
	
	/*--------- 회원가입 ---------*/
	
	// 회원가입 정보입력 받아 가입진행
//	@PostMapping("signup-fin")
//	public String signupFin(Member member) {
//		
//		
//		
//		return null;
//	}
	
	
	
	
	
	
	
	
	/*--------- 비밀번호 찾기 ---------*/

	// 비밀번호 찾기 첫페이지(정보입력)로 포워드
	@GetMapping("pw-find")
	public String pwFind() { 
		return "member/pw-find";
	}
	
	// 비밀번호 찾기 정보 입력 후 아이디 확인페이지로 포워드
	@PostMapping("pw-find")
	public String pwFind(String memberId, String memberName) { 
		return "member/pw-find-confirm";
	}
	
	// 비밀번호 변경 후 메세지+메인으로 리다이렉트 POST
	
	
	
	/*--------- 아이디 찾기 ---------*/

	// 아이디 찾기 첫페이지(정보입력)로 포워드
	@GetMapping("id-find")
	public String idFind() { 
		return "member/id-find";
	}
	
	// 아이디 찾기 정보 입력 후 아이디 확인페이지로 포워드
	@PostMapping("id-find")
	public String idFind(String memberEmail, String memberName) { 
		return "member/id-find-confirm";
	}
	
	
	
	
	

}