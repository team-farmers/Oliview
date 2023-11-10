package com.farmers.oliview.review.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	
	/** 댓글 등록
	 * @param comment
	 * @return result
	 */
	@PostMapping("comment")
	public int insert(@RequestBody Comment comment) {
		return service.insert(comment);
	}
	
	
	/** 댓글 수정
	 * @param comment
	 * @return result
	 */
	@PutMapping("comment")
	public int update(@RequestBody Comment comment) {
		return service.update(comment);
	}
	
	/** 댓글 삭제
	 * @param commentNo
	 * @return result
	 */
	@DeleteMapping("comment")
	public int delete(@RequestBody int commentNo) {
		return service.delete(commentNo);
	}
	

}
