package com.farmers.oliview.together.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.chatting.model.service.ChattingService;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.service.EditTogetherService;
import com.farmers.oliview.together.service.TogetherService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("editTogether")
@SessionAttributes
public class EditTogetherController {

	private final EditTogetherService service;
	private final TogetherService togetherservice;
	private final ChattingService chattingService;
	
	/**
	 * 게시글 삭제
	 * @param boardNo
	 * @param loginMember
	 * @param ra
	 * @param referer
	 * @return
	 */
	
	@GetMapping("{boardNo:[0-9]+}/delete")
	public String deleteBoard(
		@PathVariable("boardNo") int boardNo,
		@SessionAttribute(value = "loginMember", required = false) Member loginMember,
		RedirectAttributes ra, 
		@RequestHeader("referer") String referer) {
	
		if (loginMember == null) {
			
			ra.addAttribute("message", "로그인 후 이용해주세요");
			return "redirect:/member/login";
		}
		
		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("boardNo", boardNo);
		paramMap.put("memberNo", loginMember.getMemberNo());
		
		int result = service.deleteBoard(paramMap);
		
		String path = null;
		String message = null;
		
		if (result >0) {		
			message ="삭제되었습니다";
			path = "redirect:/together/inven";			
		} else {
			message = "삭제실패";
			path = "redirect:/";
			
		}
		 
		ra.addAttribute("message",message);
		return path;
				
	}
				
	/**
	 * 게시글 작성 화면 전환
	 * @param boardCode
	 * @param loginMember
	 * @return
	 */
	 @GetMapping("posting")
	public String insertBoard( @SessionAttribute(value = "loginMember", required = false) Member loginMember) {
		if (loginMember == null) {
			return "redirect:/together/inven" ;
		}

		return "together/posting";
	}
				 	
	//----------------------------------------------------------------------------------------------------
				 
				 
				 
	 /**
	  * 게시글 작성
	  * @param loginMember
	  * @param together
	  * @param images
	  * @param ra
	  * @return
	  * @throws IllegalStateException
	  * @throws IOException
	  */
	 
	@PostMapping("posting")
	public String insertBoard(
			@SessionAttribute("loginMember") Member loginMember,
			Together together,
			@RequestParam(value="img", required=false) MultipartFile img,
			RedirectAttributes ra

	) throws IllegalStateException, IOException {


		together.setMemberNo(loginMember.getMemberNo());


		int boardNo = service.insertBoard(together, img);
	

		if (boardNo > 0) {
			
			Map<String, Integer> map = new HashMap<>();
			map.put("chattingNo", boardNo); // 게시글번호 -> 채팅방번호로 입력
			map.put("openMemberNo", loginMember.getMemberNo()); // 채팅방오픈회원 -> 같이먹어요 게시글 작성 회원
			map.put("loginMemberNo", loginMember.getMemberNo()); //채팅방참여회원 -> 현재 로그인한 회원번호
			
			// 채팅룸 생성
			int chattingNo = chattingService.createChattingRoom(map);
			
			ra.addFlashAttribute("message", "게시글 작성 성공");
			return String.format("redirect:/together/%d", boardNo);
		}

		
		ra.addAttribute("message", "게시글 작성 실패");

		return "redirect:insert";

	}

					
					
	
	
	/**
	 * 게시글 수정 화면 전환
	 * @param boardNo
	 * @param model
	 * @return
	 */
	
	@GetMapping("{boardNo:[0-9]+}/update")
	public String updateBoard(
			@PathVariable("boardNo") int boardNo,
			Model model) {
	

		Map<String, Object> map = new HashMap<>();
		map.put("boardNo", boardNo);

		Together together = togetherservice.board(map);

		model.addAttribute("together", together);

		return "together/postingUpdate";
	}
					
				
					

					
					
	/**
	 * 게시글 수정
	 * @param boardNo
	 * @param together
	 * @param querystring
	 * @param deleteOrder
	 * @param images
	 * @param ra
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	 @PostMapping("{boardNo:[0-9]+}/update")
	 public String updateBoard(
		 @PathVariable("boardNo") int boardNo,
		Together together, 
		String querystring, 
		String deleteOrder, 
		@RequestParam("img") MultipartFile img,
		RedirectAttributes ra ) throws IllegalStateException, IOException {
 
		
		int result = service.updateBoard(together, img, deleteOrder);
		
		
		String message = null;
		
		if(result > 0) {
			message = "게시글 수정 성공 ";
			
		}
		
		else {
			message = "게시글 수정 실패";
			
			
		}
		
		ra.addFlashAttribute("message", message);
		return "redirect:/together/" + boardNo;
				
	 }
	 
	 
	
			 


}
