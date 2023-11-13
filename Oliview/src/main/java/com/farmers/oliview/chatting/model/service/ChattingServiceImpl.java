package com.farmers.oliview.chatting.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.chatting.model.dto.ChattingRoom;
import com.farmers.oliview.chatting.model.dto.Message;
import com.farmers.oliview.chatting.model.mapper.ChattingMapper;
import com.farmers.oliview.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ChattingServiceImpl implements ChattingService{
	
	private final ChattingMapper mapper;
	
	/** 채팅방 목록 조회
	 *
	 */
	@Override
	public List<ChattingRoom> selectRoomList(int memberNo) {
		return mapper.selectRoomList(memberNo);
	}
	
	/** 채팅 상대 검색
	 *
	 */
	@Override
	public List<Member> selectTarget(Map<String, Object> map) {
		return mapper.selectTarget(map);
	}
	
	/** 채팅방 입장
	 *
	 */
	@Override
	public int checkChattingNo(Map<String, Integer> map) {
		return mapper.checkChattingNo(map);
	}

	
	/** 채팅방 생성
	 *
	 */
	@Override
	public int createChattingRoom(Map<String, Integer> map) {
    	int result = mapper.createChattingRoom(map);
    	
        return result <= 0 ? 0 : (int)map.get("chattingNo"); 
	}
	
	
	/** 채팅 읽음 표시
	 *
	 */
	@Override
	public int updateReadFlag(Map<String, Object> paramMap) {
		return mapper.updateReadFlag(paramMap);
	}
	
	
	@Override
	public List<Message> selectMessageList(Map<String, Object> paramMap) {
        System.out.println(paramMap);
        List<Message> messageList = mapper.selectMessageList(  Integer.parseInt( String.valueOf(paramMap.get("chattingNo") )));
        
        if(!messageList.isEmpty()) {
            int result = mapper.updateReadFlag(paramMap);
        }
        return messageList;
	}
	
	
	@Override
	public int insertMessage(Message msg) {
		return mapper.insertMessage(msg);
	}
	
	
}
