package com.farmers.oliview.review.model.service;

import java.util.List;

import com.farmers.oliview.review.model.dto.Comment;

public interface CommentService {

	/** 댓글 목록 조회
	 * @param reviewNo
	 * @return commentList
	 */
	List<Comment> select(int reviewNo);

	/** 댓글 등록
	 * @param comment
	 * @return result
	 */
	int insert(Comment comment);

	/** 댓글 수정
	 * @param comment
	 * @return result
	 */
	int update(Comment comment);

	/** 댓글 삭제
	 * @param commentNo
	 * @return result
	 */
	int delete(int commentNo);

}
