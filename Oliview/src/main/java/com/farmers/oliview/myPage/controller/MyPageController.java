package com.farmers.oliview.myPage.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.myPage.service.MyPageService;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;

/**
 * 
 */
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
	
	
	/* -------- 내프로필 정보수정  -------- */

	/* -------- 프로필 이미지수정  -------- */
	
	/** 프로필 이미지 수정
	 * @param profileImg : 업로드된 프로필 이미지
	 * @param loginMember
	 * @param ra
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * 
	 * 
	 */
	@PostMapping("profile")
	public String profile(
			@RequestParam("profileImg") MultipartFile profileImg,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) throws IllegalStateException, IOException {
		
		int result = service.profile(profileImg, loginMember);
		
		// 서비스 결과에 따라 응답 제어
		String message = null;
		
		if(result > 0) {
			message = "프로필 이미지가 변경 되었습니다.";
			
		} else {
			message = "프로필 이미지를 선택 후 변경해주세요.";
		}
		
		ra.addFlashAttribute("message", message);
		
		// 프로필 페이지로 리다이렉트
		return "redirect:profile";
		
	}
	
	
	/** 닉네임 수정
	 * @param updateMember
	 * @param loginMember
	 * @param ra
	 * @return
	 * 
	 * 
	 * 
	 */
	@PostMapping("info/profile")
	public String editProfile(Member updateMember,
			@SessionAttribute("loginMember") Member loginMember, RedirectAttributes ra) {
		
		
		updateMember.setMemberNo(loginMember.getMemberNo());
		
		int result = service.editProfile(updateMember);
		
		String message = null;
		
		if(result > 0) {
			message = "회원 정보가 수정 되었습니다.";
			loginMember.setMemberNickname(updateMember.getMemberNickname());
			
		} else {
			message = "회원 정보 수정 실패하였습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:/myPage/profile";
	}
	
	
	// 이메일 수정
	@PostMapping("info/info")
	public String editInfo(Member updateMember,
			@SessionAttribute("loginMember") Member loginMember, RedirectAttributes ra) {
		
		
		updateMember.setMemberNo(loginMember.getMemberNo());
		
		int result = service.editInfo(updateMember);
		
		String message = null;
		
		if(result > 0) {
			message = "회원 정보가 수정 되었습니다.";
			loginMember.setMemberEmail(updateMember.getMemberEmail());
			
		} else {
			message = "회원 정보 수정 실패하였습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:/myPage/profile";
	}
	
	
	
	/** 새 비밀번호로 변경
	 * @param loginMember
	 * @param inputMember
	 * @param newPw
	 * @param ra
	 * @return
	 */
	@PostMapping("changePw")
	public String changePw(@SessionAttribute("loginMember") Member loginMember,
			String memberPw, String newPw, RedirectAttributes ra) {
		
		
		int result = service.changePw(memberPw, loginMember, newPw);
		
		String message = null;
		
		// 비밀번호 변경 성공 시
		if(result > 0) {
			message = "비밀번호 변경이 완료되었습니다.";

			// session에 변경된 비밀번호 저장
			loginMember.setMemberPw(newPw);
			
		} else { // 비밀번호 불일치 시
			message = "비밀번호가 일치하지 않습니다. 다시 시도해주세요.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:/myPage/changePw";
	}
	
	
	
	/** 내가 쓴 글 목록 조회
	 * @param model
	 * @param loginMember
	 * @param cp : 현재 페이지 정보
	 * @param paramMap
	 * @return
	 */
	@GetMapping("my-article")
	public String myArticle(Model model,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue ="1") int cp,
			@RequestParam Map<String, Object> paramMap) {
		
		// 내가쓴 글 목록 조회
		Map<String, Object> map = service.selectMyArticleList(loginMember, cp);
		
		model.addAttribute("map", map);
		return "myPage/my-article";
	}
	

	
	// 내가 쓴 댓글로 포워드
	@GetMapping("my-comment")
	public String myComment(Model model,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue ="1") int cp,
			@RequestParam Map<String, Object> paramMap) { 
		
		return "myPage/my-comment";
	}
	
	
	
	
	/** 내가 찜한 글 목록 조회
	 * @param model
	 * @param loginMember
	 * @param cp
	 * @param paramMap
	 * @return
	 */
	@GetMapping("choice-article")
	public String choiceArticle(Model model,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue ="1") int cp,
			@RequestParam Map<String, Object> paramMap) { 
		
		// 내가 찜한 글 목록 조회
		Map<String, Object> map = service.choiceArticleList(loginMember, cp);
		
		model.addAttribute("map", map);
		
		return "myPage/choice-article";
	}
	
	

}
