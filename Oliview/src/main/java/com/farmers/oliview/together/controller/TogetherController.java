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
@SessionAttributes({ "loginMember" })
@RequiredArgsConstructor
public class TogetherController {

	private final TogetherService service;

	/**
	 * 같이먹어요 메인페이지 포워드 게시글 목록 조회
	 * 
	 * @param model : 데이터 전달용 객체
	 * @param cp    : 현재 페이지(요청 시 없으면 기본 값 1)
	 * @param
	 * @return
	 */

	@GetMapping("inven")
	public String inven(Model model, @RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
	        @RequestParam Map<String, Object> paramMap) {

	    if (paramMap != null && paramMap.get("key") == null && paramMap.get("query") == null) {
	        Map<String, Object> boardMap = service.selectBoardList(cp);
	        model.addAttribute("map", boardMap);
	    } else {
	        Map<String, Object> searchMap = service.searchBoardList(paramMap, cp);
	        model.addAttribute("map", searchMap);
	    }

	    return "together/inven";
	}


	/**
	 * 게시글 상세 조회
	 * 
	 * @param boardNo
	 * @param model
	 * @param ra
	 * @param loginMember
	 * @param req
	 * @param resp
	 * @return
	 * @throws ParseException
	 */

	@GetMapping("{boardNo:[0-9]+}")
	public String board(@PathVariable("boardNo") int boardNo, Model model, RedirectAttributes ra,
	        @SessionAttribute(value = "loginMember", required = false) Member loginMember, HttpServletRequest req,
	        HttpServletResponse resp) throws ParseException {

	    Map<String, Object> map = new HashMap<>();
	    map.put("boardNo", boardNo);


	    Together together = service.board(map);

	    String path = null;

	    if (together != null) {
	        model.addAttribute("together", together);
	        path = "together/board";
	    } else {
	        // 조회 결과가 없을 때의 처리
	        path = "redirect:/together/inven";
	    }
	    
	    
	    
	    
            // 쿠키를 이용한 조회 수 증가 처리

            // 1) 비회원 또는 로그인한 회원의 글이 아닌 경우
            if (loginMember.getMemberNo() != together.getMemberNo()) {

               // 2) 쿠키 얻어오기
               Cookie c = null;

               // 요청에 담겨있는 모든 쿠키 얻어오기
               Cookie[] cookies = req.getCookies();

               if (cookies != null) { // 쿠키가 존재할 경우

                  // 쿠키 중 "readBoardNo"라는 쿠키를 찾아서 c에 대입
                  for (Cookie cookie : cookies) {
                     if (cookie.getName().equals("boardNo")) {
                        c = cookie;
                        break;
                     }
                  }
               }

               // 3) 기존 쿠키가 없거나(c == null)
               // 존재는 하나 현재 게시글 번호가
               // 쿠키에 저장되지 않은 경우 (오늘 해당 게시글 본적 없음)
               int result = 0;

               if (c == null) {
                  // 쿠키가 존재 X -> 하나 새로 생성
                  c = new Cookie("boardNo", "|" + boardNo + "|");

                  // 조회수 증가 서비스 호출
                  result = service.updateReadCount(boardNo);

               } else {

                  // 현재 게시글 번호가 쿠키에 있는지 확인

                  // Cookie.getValue() : 쿠키에 저장된 모든 값을 읽어옴
                  // -> String으로 반환

                  // String.indexOf("문자열")
                  // : 찾는 문자열이 String이 몇번 인덱스에 존재하는지 반환
                  // 단, 없으면 -1 반환

                  if (c.getValue().indexOf("|" + boardNo + "|") == -1) {
                     // 쿠키에 현재 게시글 번호가 없다면

                     // 기존 값에 게시글 번호 추가해서 다시 세팅
                     c.setValue(c.getValue() + "|" + boardNo + "|");

                     // 조회수 증가 서비스 호출
                     result = service.updateReadCount(boardNo);
                  }
               } // 3) 종료

               // 4) 조회 수 증가 성공 시
               // 쿠키가 적용되는 경로, 수명(당일 23시 59분 59초) 지정

               if (result > 0) {
                  together.setReadCount(together.getReadCount() + 1);
                  // 조회된 board 조회 수와 DB 조회 수 동기화

                  // 적용 경로 설정
                  c.setPath("/"); // "/" 이하 경로 요청 시 쿠키 서버로 전달

                  // 수명 지정
                  Calendar cal = Calendar.getInstance(); // 싱글톤 패턴
                  cal.add(cal.DATE, 1); // 24시간 후의 시간을 기록

                  // 날짜 표기법 변경 객체 (DB의 TO_CHAR()와 비슷)
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                  // java.util.Date
                  Date a = new Date(); // 현재 시간
                  // 2023-10-31 14:30:14

                  Date temp = new Date(cal.getTimeInMillis()); // 다음날 (24시간 후)
                  // 2023-11-01 14:30:14

                  Date b = sdf.parse(sdf.format(temp)); // 다음날 0시 0분 0초
                  // 다음날 0시 0분 0초 - 현재 시간
                  long diff = (b.getTime() - a.getTime()) / 1000;
                  // -> 다음날 0시 0분 0초까지 남은 시간을 초단위로 반환

                  c.setMaxAge((int) diff); // 수명 설정

                  resp.addCookie(c);    // 응답 객체를 이용해서
                  // 클라이언트에게 전달
               }
            }
            map.put("memberNo", loginMember.getMemberNo());
        
           
            
      
     
	    

	    return path;
	}

}