package com.farmers.oliview.together.service;

import java.util.Map;

import com.farmers.oliview.together.dto.Together;

public interface TogetherService {
	
	/**
	 * 게시글 목록 조회
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectBoardList( int cp);
	
	/** 검색 목록 조회
	 * @param cp
	 * @return boardList
	 */
	
	Map<String, Object> searchBoardList(Map<String, Object> paramMap, int cp);
	
	

	
	
	/**
	 * 게시글 상세조회
	 * @param map
	 * @return
	 */
	Together board(Map<String, Object> map);
	

	/**조회수 증가
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);
	
	
	
	


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
