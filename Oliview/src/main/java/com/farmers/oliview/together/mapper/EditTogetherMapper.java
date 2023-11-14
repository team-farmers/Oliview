package com.farmers.oliview.together.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.together.dto.BoardImg;
import com.farmers.oliview.together.dto.Together;

@Mapper
public interface EditTogetherMapper {
	
	/**
	 * 게시글 부분만 삽입
	 * @param together
	 * @return
	 */
	int insertBoard(Together together);
	
	
	/**
	 * 업로드된 이미지 정보 일괄 삽입
	 * @param uploadList
	 * @return
	 */

	int insertUploadList(List<BoardImg> uploadList);

	/**
	 * 게시글 삭제
	 * @param paramMap
	 * @return
	 */
	int deleteBoard(Map<String, Integer> paramMap);


	/**
	 * 이미지 삭제
	 * @param map
	 * @return
	 */
	int imageDelete(Map<String, Object> map);

	/**
	 * 게시글 부분만 수정
	 * @param together
	 * @return
	 */
	int updateBoard(Together together);

	/**
	 * 이미지 하나 수정
	 * @param img
	 * @return
	 */
	int updateBoardImg(BoardImg img);

	/**
	 * 이미지 하나 삽입
	 * @param img
	 */
	void boardImgInsert(BoardImg img);


	
	
	
	
	
	
	
	
	
	
	
	

}
