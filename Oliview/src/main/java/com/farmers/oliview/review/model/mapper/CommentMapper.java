package com.farmers.oliview.review.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.review.model.dto.Comment;

@Mapper
public interface CommentMapper {

	/** 댓글 목록 조회
	 * @param reviewNo
	 * @return commentList
	 */
	List<Comment> select(int reviewNo);

}
