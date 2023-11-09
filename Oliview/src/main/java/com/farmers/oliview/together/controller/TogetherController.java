package com.farmers.oliview.together.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.farmers.oliview.together.dto.BoardImg;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.service.TogetherService;

import jakarta.servlet.http.Cookie;
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
	
	

	/**
	 * 게시글 목록 조회
	 * @param boardCode : 게시판 종류 번호
	 * @param model : 데이터 전달용 객체
	 * @param cp : 현재 페이지(요청 시 없으면 기본 값 1)
	 * @param 
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}")
	public String selectBoardList(
			@PathVariable("boardCode") int boardCode,
			Model model,
			@RequestParam(value="cp" , required= false, defaultValue="1") int cp ,
			@RequestParam Map<String , Object> paramMap){
				
			
			
			 if(paramMap.get("key") == null && paramMap.get("query")== null) {
		
		
			Map<String, Object> map = service.selectBoardList(boardCode, cp);
			
			
			model.addAttribute("map",map);
			
			 }
			 
			 /* 검색인 경우 */
			 
			 else {
				 
				 // boardCode를 paramMap에 추가 (한번에 묶어서 sql 전달예정)
				 paramMap.put("boardCode", boardCode);
				 
				 Map <String, Object> map = service.searchBoardList(paramMap, cp);
				 model.addAttribute("map",map);
				 
			 }
		return "together/inven";
		
	}
	
		/**
		 * 게시글 상세 조회 
		 * @param boardCode
		 * @param boardNo
		 * @param model
		 * @param ra
		 * @param loginMember
		 * @param req
		 * @param resp
		 * @return
		 * @throws ParseException 
		 */
	
		@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
		public String boardDetail(@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo,
				Model model, RedirectAttributes ra,
				@SessionAttribute(value = "loginMember", required=false) Member loginMember,
				  HttpServletRequest req, HttpServletResponse resp) throws ParseException {
			
			
				Map<String,Object> map =new HashMap<>();
				map.put ("boardCode", boardCode);
				map.put("boardNo",boardNo);
				
				Together together = service.boardDetail(map);
				
				/*조회 결과 있으면 board.board/Detail로 포워드*/
				/*없으면 redirect:/board/{boardCode}  + 게시글 없음 이미지*/
				String path= null;
				
				/*조회 결과가 있을때*/
				if(together != null) {
					model.addAttribute("together", together);
					
					path = "together/board";
				}
				
				
				/* 조회수 증가 */
				if(loginMember == null ||
						loginMember.getMemberNo() != together.getMemberNo()){
							
					        /*쿠키 얻어오기 */
							Cookie c = null;
							
							/*요청 담긴 모든 쿠키 얻어오기 */
							Cookie[] cookies = req.getCookies();
							
							if(cookies != null) {
								
								for(Cookie cookie : cookies) {
									
									if(cookie.getName().equals("readBoardNo")) {
										
										c = cookie;
										
										break;							
									}
									
								}
							
							}
							
							/* 기존 쿠키가 없거나 존재하지만 게시글 번호가 쿠키에 저장 x*/
							
							
							int result = 0;
							
							if(c == null) {
								
								/* 쿠키가 존재하지 않으면 하나 새로 생성됨*/
								c= new Cookie ("readBoardNo", "|" + boardNo + "|");
								
								/* 조회수 증가 서비스 호출 */
								result = service.updateReadCount(boardNo);
								
								/* 현재 게시글 번호가 쿠키에 있는지 확인*/
							} else { 
								
								/*쿠키에 현재 게시글 번호가 없다면*/
								if(c.getValue().indexOf("|" + boardNo + "|") ==  -1) {
									/* 기존 값에 게시글 번호 추가해서 다시 세팅*/
									c.setValue(c.getValue() + "|" + boardNo + "|");
									
									result = service.updateReadCount(boardNo);
								}

							} /*finsh*/
							
							/* 조회수 증가 성공 시 쿠키가 적용되는 경로 , 수명(당일 23시 59분 59초) 지정 */
							
							if(result > 0) {
								together.setReadCount(together.getReadCount() +1); 
								
								/*적용 경로 설정*/
								c.setPath("/");
								
								/*수명 지정*/
								Calendar cal = Calendar.getInstance();
								/*24시간 후의 시간 기록*/
								cal.add(cal.DATE, 1);
								
								/*날짜 표기법 변경 객체*/
								SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
								
								/* 현재시간 */
								Date a = new Date();
								
								
								Date temp = new Date(cal.getTimeInMillis());
								
								/* 다음날 0시 0분 0초*/
								Date b = sdf.parse(sdf.format(temp));
								
								/* 다음날 0시 0분 0초까지 남은 시간을 초단위로 반환 */
								long diff = (b.getTime() - a.getTime()) /  1000;
								
								/* 수명 설정 */
								c.setMaxAge((int) diff);
								
								/* 응답객체를 이용해서 클라이언트에게 전달*/
								resp.addCookie(c);
								
							}
							
						}
				
					/*----------------------------------------------------------*/
					
				
						if(together.getImageList().size() > 0) {
							
						   BoardImg thumbnail = null;
						   
						   /* 썸네일이 존재하면 */
						   if(together.getImageList().get(0).getImgOrder() == 0) {
							   thumbnail = together.getImageList().get(0);
						   }
						   
						   		model.addAttribute("thumbnail",thumbnail);
						   		model.addAttribute("start" , thumbnail != null ? 1 : 0);
		
					}
						
						/* 게시글이 없을 경우 */
						else { 
							path = "redirect:/board/" + boardCode;
							
							ra.addFlashAttribute("message", "해당 게시글이 존재하지 않습니다");
					}
						
						return path;
					

					}
		
	

	
	
	
	
	
	
	
	
	
}
