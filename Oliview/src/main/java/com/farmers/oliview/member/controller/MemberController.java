package com.farmers.oliview.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.member.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("member")
@RequiredArgsConstructor
@SessionAttributes({"loginMember", "referer"})
public class MemberController {
	
	private final MemberService service;
	
	
	
	/*--------- 로그인 ---------*/
	
	/** 로그인 페이지로 포워드
	 * @return
	 */
	@GetMapping("login")
	public String login(@RequestHeader(value="referer", required = false) String referer, Model model) {
		
		// 이전페이지 주소 저장해 전달
		if(referer != null) model.addAttribute("referer", referer);
		
		return "member/login";
	}
	
	
	/** 로그인 서비스
	 * @param inputMember
	 * @param model
	 * @param ra
	 * @param referer : 요청 헤더에 기록된 요청 이전페이지 주소
	 * @return loginMember
	 */
	@PostMapping("login")
	public String login(Member inputMember, Model model, RedirectAttributes ra,
			String saveId, HttpServletResponse resp, @SessionAttribute(value="referer", required = false) String referer) {
		
		// 로그인 서비스 호출
		Member loginMember = service.login(inputMember);
		
		// 로그인 정보 일치 시
		if(loginMember != null) {
			
			// 로그인 정보 저장(쿠키)
			Cookie cookie = new Cookie("saveId", loginMember.getMemberId());
			
			if(saveId != null) {
				cookie.setMaxAge(60 * 60 * 24 * 30 * 12); // 1년 유지
			} else {
				cookie.setMaxAge(0);
			}
			
			cookie.setPath("/");
			resp.addCookie(cookie);
			
			// 회원정보 세션에 저장
			model.addAttribute("loginMember", loginMember);
			
			
			// 이전페이지로 리다이렉트
			if(referer == null) 		return "redirect:/";
			
			return "redirect:" + referer; // 
		} 
		
		// 로그인 정보 불일치시
		if(loginMember == null) {
			ra.addFlashAttribute("message", "회원정보가 일치하지 않습니다.");
		}

		// 로그인페이지 리다이렉트
		return "redirect:login";
	}
	
	
	
	/*--------- 로그아웃 ---------*/
	
	/** 로그아웃
	 * @param status
	 * @return
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
	
	
	
	
	/*--------- 회원가입 (forward)---------*/
	
	/** 회원가입 메인 페이지로 포워드
	 * @return
	 */
	@GetMapping("signup")
	public String signup() {
		return "member/signup";
	}
	
	
	/** 회원가입 약관동의 후 상세정보입력 페이지로 포워드
	 * @return
	 */
	@GetMapping("signup-agree")
	public String signupAgree() { // 동의정보 체크해서 저장해놔야함
		return "member/signup-info";
	}

	
	
	/*--------- 회원가입 ---------*/
	
	/** 회원가입 서비스
	 * @param inputMember
	 * @param ra
	 * @return
	 */
	@PostMapping("signup-fin")
	public String signup(Member inputMember, RedirectAttributes ra) {
		
		// 회원가입 서비스 호출
		int result = service.signup(inputMember);
		
		// 가입 성공 시
		if(result > 0) {
			ra.addFlashAttribute("message", "환영합니다! Oliview 회원 가입이 성공하였습니다");
			return "member/signup-fin";
		}
		
		ra.addFlashAttribute("message", "회원가입이 실패하였습니다.");
		return "redirect:/";
	}
	
	
	
	/** 아이디 중복 검사
	 * @param id
	 * @return
	 */
	@GetMapping("checkId")
	@ResponseBody
	public int checkId(String id){
		return service.checkId(id);
	}
	
	
	/** 이메일 중복 검사
	 * @param email
	 * @return
	 */
	@GetMapping("checkEmail")
	@ResponseBody
	public int checkEmail(String email){
		return service.checkEmail(email);
	}
	
	
	 /** 닉네임 중복 검사
	 * @param nickname
	 * @return
	 */
	@GetMapping("checkNickname")
	@ResponseBody
	public int checkNickname(String nickname) {
		return service.checkNickname(nickname);
	}
	
	
	
	
	/*--------- 비밀번호 찾기 ---------*/

	/** 비밀번호 찾기 첫페이지(정보입력)로 포워드
	 * @return
	 */
	@GetMapping("pw-find")
	public String pwFind() { 
		return "member/pw-find";
	}
	
	/** 비밀번호 찾기
	 * @param inputMember
	 * @param model
	 * @param ra
	 * @return
	 */
	@PostMapping("pw-find")
	public String pwFind(Member inputMember, Model model, RedirectAttributes ra) {
		
		// 입력된 정보가 일치하는 회원유무 확인 
		int result = service.memberPwFind(inputMember);
		
		// 일치하는 회원이 있을 경우 회원 정보 가져오기
		if (result > 0) {
			Member searchMember = service.pwFind(inputMember);
			
			model.addAttribute("searchMember", searchMember);
			 
			return "member/pw-find-confirm";
		}
		
		
		// 일치하지 않을 경우 비번찾기 페이지로 리다이렉트
		ra.addFlashAttribute("message", "정보가 일치하는 회원이 존재하지 않습니다.");
		
		return "redirect:pw-find";
	}
	
	
	
	/** 비밀번호 변경 후 메세지+메인으로 리다이렉트 POST
	 * @param inputMember
	 * @param ra
	 * @param status
	 * @return
	 */
	@PostMapping("changePw")
	public String changePw(Member inputMember, RedirectAttributes ra, SessionStatus status) {
		
		// 비밀번호 변경 서비스 호출
		int result = service.changePw(inputMember);
		
		// 비밀번호 변경 성공시
		if(result > 0) {
			ra.addFlashAttribute("message", "비밀번호가 바뀌었습니다. 기존 비밀번호로 로그인된 기기에서 모두 로그아웃 되었습니다.");
			
			// 로그아웃
			status.setComplete();

			// 로그인페이지 리다이렉트
			return "redirect:login";
		}
		
		ra.addFlashAttribute("message", "비밀번호 변경이 실패하였습니다. 다시 시도해주세요.");
		
		return "member/pw-find-confirm";
	}
	
	
	
	
	/*--------- 아이디 찾기 ---------*/

	/** 아이디 찾기 첫페이지(정보입력)로 포워드
	 * @return
	 */
	@GetMapping("id-find")
	public String idFind() { 
		return "member/id-find";
	}
	
	/** 아이디 찾기 서비스
	 * @param inputMember
	 * @param model
	 * @param ra
	 * @return
	 */
	@PostMapping("id-find")
	public String idFind(Member inputMember, Model model, RedirectAttributes ra) { 
		 // 일치되는 정보가 있으면 1, 없으면 0 반환
		 int result = service.memberFind(inputMember);
		 
		 // 일치하는 회원있을 경우 아이디 정보 가져오기
		 if (result > 0) {
			 String memberId = service.idFind(inputMember);
			 
			 model.addAttribute("memberId", memberId);
			 
			 return "member/id-find-confirm";
		 }
		
		 ra.addFlashAttribute("message", "정보가 일치하는 회원이 존재하지 않습니다.");
		 
		return "redirect:id-find";
	}
	

}
