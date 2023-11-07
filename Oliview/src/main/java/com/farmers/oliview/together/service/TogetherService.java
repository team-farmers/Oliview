package com.farmers.oliview.together.service;

import java.util.Map;

public interface TogetherService {
	
	/**
	 * 게시글 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return
	 */

	Map<String, Object> selectBoardList(int boardCode, int cp);

}
