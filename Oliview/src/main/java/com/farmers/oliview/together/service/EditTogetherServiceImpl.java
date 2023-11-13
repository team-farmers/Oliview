package com.farmers.oliview.together.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.farmers.oliview.common.utility.Util;
import com.farmers.oliview.together.dto.BoardImg;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.exception.BoardUpdateException;
import com.farmers.oliview.together.mapper.EditTogetherMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:/config.properties")
public class EditTogetherServiceImpl implements EditTogetherService {
	
	private final EditTogetherMapper mapper;
	
	
	//@Value("${my.together.location}")
	//private String folderPath;
	
	
	@Value("${my.together.webpath}")
	private String webPath;
	
	@Value("${my.together.location}")
	private String folderPath;
	
	// 게시글 작성
	@Override
	public int insertBoard(Together together, MultipartFile img) throws IllegalStateException, IOException {
		
		String rename = Util.fileRename(img.getOriginalFilename());
		together.setBoardImg(webPath+rename);
				
		int result = mapper.insertBoard(together);
		
		int boardNo = 0;
		if(result == 1) {
			boardNo = together.getBoardNo();
			
			img.transferTo(new File(folderPath + rename));
		}
			
		return boardNo;
	}
	
	//게시글 삭제
	@Override
	public int deleteBoard(Map<String, Integer> paramMap) {
		
		return mapper.deleteBoard(paramMap);
	}

	
	// 게시글 수정
	@Override
	public int updateBoard(Together together, MultipartFile img, String deleteOrder) throws IllegalStateException, IOException {
		
		
		// 1. 이미지를 삭제한 경우
		if (!deleteOrder.equals("")) { 
			together.setBoardImg("-1");
		}
		
		// 2. 변경된 이미지가 있을 경우 
		String rename = null;
		if(img.getSize() > 0) {
			rename = Util.fileRename(img.getOriginalFilename());
			together.setBoardImg(webPath+rename);
		}
	    
	    // 게시글(제목, 내용) 수정
	    int result = mapper.updateBoard(together);

	    // 수정 실패 시
	    if(result == 0) return 0;


	    // 3. 새로 업로드된 이미지 분류 작업
	    if (img.getSize() > 0) { // 이미지가 업로드된 경우에만 처리
	    	img.transferTo(new File(folderPath + rename));
	    }

	    return result;
	}

		
		
		
	}















	
	
	
	
	
	
	
	
	
	
	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		







