package com.farmers.oliview.review.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.review.model.dto.Comment;
import com.farmers.oliview.review.model.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	
	private final CommentMapper mapper;
	
	
	// 댓글 목록 조회
	@Override
	public List<Comment> select(int reviewNo) {
		return mapper.select(reviewNo);
	}

	
}
