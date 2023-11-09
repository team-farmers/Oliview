package com.farmers.oliview.review.model.service;

import java.util.List;

import com.farmers.oliview.review.model.dto.Comment;

public interface CommentService {

	/** 댓글 목록 조회
	 * @param reviewNo
	 * @return commentList
	 */
	List<Comment> select(int reviewNo);

}
