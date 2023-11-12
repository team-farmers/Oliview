package com.farmers.oliview.together.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	public int insertBoard(Together together, List<MultipartFile> images) throws IllegalStateException, IOException {
		
		
		int result = mapper.insertBoard(together);
		
		
		int boardNo = together.getBoardNo();
		
		List<BoardImg> uploadList = new ArrayList<>();
		
		for(int i = 0; i <images.size() ; i++) {
			
		
			if(images.get(i).getSize() > 0) {
				
				BoardImg img = new BoardImg();
				
				img.setBoardNo(boardNo);
				img.setImgOrder(i); 
				
				img.setImgOriginalName(images.get(i).getOriginalFilename()); 
				
				
				
				img.setImgPath(webPath);
				
			
				img.setImgRename(Util.fileRename(images.get(i).getOriginalFilename()));
				
				
				img.setUploadFile(images.get(i));
				
				
			
				uploadList.add(img);
				
			}
		}  
		
		if(uploadList.isEmpty()) {
			
			return boardNo;
		}
		
		

		result = mapper.insertUploadList(uploadList);
		

		if(result == uploadList.size()) {
			
			
			
			for(BoardImg img : uploadList) {
				
				img.getUploadFile().transferTo(new File(folderPath + img.getImgRename()));
				
			}
			
		}else { 
			
			throw new RuntimeException("파일 정보 DB 삽입 실패");
			
		}

			return boardNo;
	
		}
	
		//게시글 삭제
		@Override
		public int deleteBoard(Map<String, Integer> paramMap) {
			
			return mapper.deleteBoard(paramMap);
		}
	
		
		//게시글 수정
		@Override
		public int updateBoard(Together together, List<MultipartFile> images, String deleteOrder) throws IllegalStateException, IOException {
				
				int result = mapper.updateBoard(together);
				
				if(result == 0) 
					return 0;
				
				
				/*deleteOrder의 내용이 존재하면 삭제 수행*/

					if(!deleteOrder.equals("")) {
						
						Map <String,Object> map = new HashMap<>();
						
						map.put("boardNo",together.getBoardNo());
						map.put("deleteOrder", "deleteOrder");
						
						result = mapper.imageDelete(map);
						
						if(result == 0) {
							throw new BoardUpdateException("이미지 삭제 실패");
						}

					}
			
		
						List<BoardImg> uploadList = new ArrayList<>();
						
						for(int i=0 ; i<images.size() ; i++) {
							
							if(images.get(i).getSize() > 0) {
								
								BoardImg img = new BoardImg();
								
								
								img.setBoardNo(together.getBoardNo()); /*몇번 게시글의 이미지*/
								img.setImgOrder(i); /* 몇 번째 이미지*/
								
								
								img.setImgOriginalName(images.get(i).getOriginalFilename()); /* 원본 파일명 */
								
								
								img.setImgPath(webPath); /*웹 접근 경로*/
								
								img.setImgRename(Util.fileRename(images.get(i).getOriginalFilename())); /* 변경된 파일명 */
								
								img.setUploadFile(images.get(i));
								
								uploadList.add(img);
								
								
								result = mapper.updateBoardImg(img); /* 있다 -> 변경 */
								
								
								if(result == 0) {
									mapper.boardImgInsert(img); /* 없다 -> 추가 */
								}
								
								
							}		
							
							
							
						}
							
						/* upload list에 있는 이미지를 서버에 저장*/
						if( !uploadList.isEmpty() ) {
							result = 1;
							
							for(BoardImg img : uploadList) {
								
								img.getUploadFile().transferTo(new File( folderPath + img.getImgRename() ) );
							}
						}
					
						return result;
					}
					
			
			
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		







