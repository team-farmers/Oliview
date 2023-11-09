package com.farmers.oliview.review.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmers.oliview.review.model.dto.Comment;
import com.farmers.oliview.review.model.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService service;
	
	
	/** 리뷰 번호에 따른 게시글의 댓글 목록 모두 조회
	 * @param reviewNo
	 * @return commentList
	 */
	@GetMapping(value="comment", produces="application/json")
	public List<Comment> select(int reviewNo){
		return service.select(reviewNo);
	}

}
