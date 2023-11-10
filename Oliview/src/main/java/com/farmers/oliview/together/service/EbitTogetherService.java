package com.farmers.oliview.together.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.farmers.oliview.together.dto.Together;

public interface EbitTogetherService {
	
	
	/**
	 * 게시글 작성 삽입
	 * @param together
	 * @param images
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	int insertBoard(Together together, List<MultipartFile> images) throws IllegalStateException, IOException;

	
	/**
	 * 게시글 삭제
	 * @param paramMap
	 * @return
	 */
	int deleteBoard(Map<String, Integer> paramMap);

	/**
	 * 게시글 수정
	 * @param together
	 * @param images
	 * @param deleteOrder
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	int updateBoard(Together together, List<MultipartFile> images, String deleteOrder) throws IllegalStateException, IOException;
	
	


}
