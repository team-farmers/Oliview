package com.farmers.oliview.together.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.farmers.oliview.together.service.TogetherService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("together")
@SessionAttributes("loginMember")
@RequiredArgsConstructor
public class TogetherController {
	
	private final TogetherService service;

	
	/**
	 * 게시글 목록 조회
	 * @param boardCode : 게시판 종류 번호
	 * @param model : 데이터 전달용 객체
	 * @param cp : 현재 페이지
	 * @return
	 */
	
	@GetMapping("{boardCode:[0-9]+}")
	public String selectBoardList(
			
			@PathVariable("boardCode") int boardCode,
			Model model, 
			@RequestParam(value="cp" , required= false, defaultValue="1") int cp) {
		
			
			Map<String,Object> map = service.selectBoardList(boardCode,cp);
		
			
			model.addAttribute("map",map);
			
			return "board/inven";
		
	}
			
			
			
			
	
	
	
	
	
	
	
	
	

}
