package com.farmers.oliview.together.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.farmers.oliview.together.dto.Together;

@Mapper
public interface TogetherMapper {

	/** 전체 게시글 수 조회
	 * @return listCount
	 */
	int getListCount();

	
	/** 게시글 목록 조회
	 * @param temp
	 * @param rowBounds 
	 * @return boardList
	 */
	List<Together> selectBoardList(String temp, RowBounds rowBounds);



	/**
	 * 조회수 증가
	 * @param boardNo
	 * @return
	 */

	int updateReadCount(int boardNo);
	
	/**
	 *  검색어 일치 게시글 수 조회
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
	 * 게시글 상세조회 
	 * @param map
	 * @return
	 */

	Together board(Map<String, Object> map);


	
	
}
