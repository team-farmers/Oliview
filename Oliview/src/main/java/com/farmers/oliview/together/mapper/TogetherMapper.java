package com.farmers.oliview.together.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.farmers.oliview.together.dto.Together;

@Mapper
public interface TogetherMapper {

	/** 전체 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	int getListCount(int boardCode);

	
	/** 게시글 목록 조회
	 * @param boardCode
	 * @param rowBounds 
	 * @return boardList
	 */
	List<Together> selectBoardList(int boardCode, RowBounds rowBounds);


	/** 게시글 상세 조회
	 * @param map
	 * @return board
	 */
	Together boardDetail(Map<String, Object> map);
	
	
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


	
	
}
