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
	
	
//	@Override
//	public Map<String, Object> selectBoardList(int boardCode, int cp) {
//	
//		int listCount = mapper.getListCount(boardCode);
//		
//		Pagination pagination = new Pagination(cp, listCount);
//		
//		int offset = (pagination.getCurrentPage()-1) * pagination.getLimit();
//		
//		int limit = pagination.getLimit();
//		
//		RowBounds rowBounds = new RowBounds(offset,limit);
//		
//		List<Together> boardList = mapper.selectBoardList(boardCode, rowBounds);
//		
//		Map<String, Object> map = new HashMap<>();
//		map.put("boardList", boardList);
//		map.put("pagination",pagination);
//		
//		
//		
//		return map;
//}
//	
//	@Override
//	public Map<String, Object> searchBoardList(Map<String, Object> paramMap, int cp) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	

}

