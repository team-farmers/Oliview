package com.farmers.oliview.together.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.farmers.oliview.together.dto.Together;

@Mapper
public interface TogetherMapper {
	
	/**
	 * 게시글 목록 조회
	 * @param boardCode
	 * @return
	 */
	List<Together> selectBoardList(int boardCode, RowBounds rowBounds);
	
	
	/**
	 * 검색어 일치 게시글 수 조회
	 * @param paramMap
	 * @return
	 */

	int searchListCount(Map<String, Object> paramMap);
	
	/**
	 * 검색어 일치 게시글 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Together> searchBoardList(Map<String, Object> paramMap, RowBounds rowBounds);
	
	/**
	 * 전체 게시글 수 조회
	 * @param boardCode
	 * @return
	 */

	int getListCount(int boardCode);
	
	
}
