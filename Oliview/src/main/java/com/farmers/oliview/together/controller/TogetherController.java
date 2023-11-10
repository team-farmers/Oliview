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

		if (paramMap.get("key") == null && paramMap.get("query") == null) {

			Map<String, Object> map = service.selectBoardList(cp);
			model.addAttribute("map", map);

		}

		/* 검색인 경우 */

		else {

			// boardCode를 paramMap에 추가 (한번에 묶어서 sql 전달예정)
			Map<String, Object> map = service.searchBoardList(paramMap, cp);
			model.addAttribute("map", map);

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

		/* 조회 결과 있으면 board.board/Detail로 포워드 */
		String path = null;

		/* 조회 결과가 있을때 */
		if (together != null) {
			model.addAttribute("together", together);

			path = "together/board";
		}

		return path;

	}

}
