package com.farmers.oliview.together.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.review.model.dto.Pagination;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.mapper.TogetherMapper;

import lombok.RequiredArgsConstructor;

@Service 
@Transactional (rollbackFor = Exception.class)
@RequiredArgsConstructor
public class TogetherServiceImpl implements TogetherService {
	
	private final TogetherMapper mapper;
	
	/** 게시글 목록 조회
	    * @param boardCode
	    * @param cp
	    * @return
	    */
	   @Override
	   public Map<String, Object> selectBoardList(int boardCode, int cp) {
	    

	      /* 전체 게시글 수 조회 */
	      int listCount = mapper.getListCount(boardCode);
	      
	      /* cp, listCount를 이용해서 Pagination 객체 생성 */
	      Pagination pagination = new Pagination(cp, listCount);
	      
	      
	      int offset = (pagination.getCurrentPage()-1) * pagination.getLimit();
	      
	      int limit = pagination.getLimit();
	      
	      RowBounds rowBounds = new RowBounds(offset, limit);
	      
	      
	      List<Together> boardList = mapper.selectBoardList(boardCode, rowBounds);
	      
	      // Map에 담아서 반환
	      Map<String, Object> map = new HashMap<>();
	      map.put("boardList", boardList);
	      map.put("pagination", pagination);
	      
	      return map;
	   }	
	   	
	   		/*search*/ 
			@Override
			public Map<String, Object> searchBoardList(Map<String, Object> paramMap, int cp) {
				
				/* 검색 조건 일치 게시글 수 조회*/
				int listCount = mapper.searchListCount(paramMap);
				
				/* cp, listCount를 이용해서 Pagination 객체 생성*/
				Pagination pagination = new Pagination(cp, listCount);
				
				/* offset은 시작 인덱스 번호를, limit은 조회할 레코드의 개수 */
				/* RowBounds 객체 생성 */
				int offset = (pagination.getCurrentPage() -1) * pagination.getLimit();
				int limit = pagination.getLimit();
				RowBounds rowBounds = new RowBounds (offset, limit);
				
				
				/* 검색어 일치 게시글 목록 조회 */
				List<Together> boardList = mapper.searchBoardList(paramMap, rowBounds);
				
				
				/* Map에 담아서 변환 */
				Map<String, Object>map = new HashMap<>();
				map.put("boardList", boardList);
				map.put("pagination", pagination);
				

				return map;
			}
			
			/* 게시글 상세 조회 + 게시글 댓글 모두 조회 */
			@Override
			public Together boardDetail(Map<String, Object> map) {
			
				return mapper.boardDetail(map);
			}
			
			/* 조회수 증가 */
			@Override
			public int updateReadCount(int boardNo) {
			
				return mapper.updateReadCount(boardNo);
			}
		


}

