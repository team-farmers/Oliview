package com.farmers.oliview.together.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.service.TogetherService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("together")
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
public class TogetherController { 
	
	private final TogetherService service;
	
	
	/**같이먹어요 메인페이지 포워드
	 * @return
	 */
	@GetMapping("inven")
	public String inven(){
		return "together/inven";
	}	
	
	@GetMapping("posting")
	public String posting() {
		return "together/posting";
	}
	
	@GetMapping("chat")
	public String chat() {
		return "together/chat";
	}
	
	@GetMapping("talk")
	public String talk() {
		return "together/talk";
	}
	
	@GetMapping("board")
	public String board() {
		return "together/board";
	}
	
	

	
	
//	/**
//	 * 게시글 목록 조회
//	 * @param boardCode
//	 * @param model
//	 * @param cp
//	 * @param paramMap
//	 * @return
//	 */
//	
//	@GetMapping("{boardCode:[0-9]+}")
//	public String selectBoardList(
//			@PathVariable("boardCode") int boardCode,
//			Model model,
//			@RequestParam(value="cp" , required= false, defaultValue="1") int cp ,
//			@RequestParam Map<String , Object> paramMap){
//			
//			
//			if(paramMap.get("key") == null && paramMap.get("query")== null) {
//		
//		
//			Map<String, Object> map = service.selectBoardList(boardCode, cp);
//			
//			
//			model.addAttribute("map",map);
//			
//			 }
//			 
//			 // 검색인 경우
//			 else {
//				 
//				 paramMap.put("boardCode", boardCode);
//				 
//				 Map <String, Object> map = service.searchBoardList(paramMap, cp);
//				 model.addAttribute("map",map);		 
//			 }
//				return "together/inven";
//		
//			}
//	
//		@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
//		public String board(@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo,
//				Model model, RedirectAttributes ra,
//				@SessionAttribute (value = "loginMember" , required=false)Member loginMember,
//		         HttpServletRequest req, HttpServletResponse resp) {
//				
//			    // 1. 상세 조회 서비스 호출
//				Map <String, Object> map = new HashMap<>();
//				map.put("boardCode", boardCode);
//			    map.put("boardNo", boardNo);
//			    
//			    Together together = service.board(map);
//			    
//			    // 조회 결과가 있으면 board/boardDetail로 포워드
//			    // 없으면 redirect:/board/{boardCode} + 게시글 없음 메시지
//			     String path = null;
//			     
//			    // 2. 조회 결과가 있을 때
//			     if(together != null) {
//			    	 model.addAttribute("together", together);
//			    	 
//			    	 path = "together/inven";
//			    	 
//			   	 
//			    	 
//			    	 
//			    	 
//			    	 
//			     }
			     
			
			
//		}
			 
	
	
	
	
	
	
	
	
	
	
	
	
	
}
