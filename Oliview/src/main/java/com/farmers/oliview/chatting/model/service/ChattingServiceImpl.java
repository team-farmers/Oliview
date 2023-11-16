package com.farmers.oliview.chatting.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.chatting.model.dto.ChattingRoom;
import com.farmers.oliview.chatting.model.dto.Message;
import com.farmers.oliview.chatting.model.mapper.ChattingMapper;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.together.dto.Together;

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
	
	/** 채팅방 존재유무확인
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
		
		// 채팅방 개설자 회원번호 얻어오기
		int openMemberNo = mapper.searchOpenMember(map);
		map.put("openMemberNo", openMemberNo);
		
    	int result = mapper.createChattingRoom(map);
    	
    	// 결과가 0보다 같거나 작다? retrun 0, 아닐경우(생성성공) chattingNo 리턴
        return result <= 0 ? 0 : (int)map.get("chattingNo"); 
	}
	
	
	/** 채팅 읽음 표시
	 *
	 */
	@Override
	public int updateReadFlag(Map<String, Object> paramMap) {
		return mapper.updateReadFlag(paramMap);
	}
	
	
	/** 채팅 불러오기
	 *
	 */
	@Override
	public List<Message> selectMessageList(Map<String, Object> paramMap) {
        List<Message> messageList = mapper.selectMessageList(  Integer.parseInt( String.valueOf(paramMap.get("chattingNo") )));
        
        // 채팅 기록이 없다면
        if(!messageList.isEmpty()) {
        	
        	// 내가 읽지 않은 메세지는 모두 읽음으로 바꾸기
            int result = mapper.updateReadFlag(paramMap);
        }
        return messageList;
	}
	
	
	/** 채팅 메세지 DB 입력
	 *
	 */
	@Override
	public int insertMessage(Message msg) {
		return mapper.insertMessage(msg);
	}
	
	/** 채팅 참여하는 같이먹어요 게시글 정보 조회
	 *
	 */
	@Override
	public Together talkTogether(int boardNo) {
		return mapper.talkTogether(boardNo);
	}
	
	
	/** 채팅방 목록에서 클릭 후 -> 채팅방이 존재하는지 여부 확인 
	 *
	 */
	@Override
	public int checkChattingPart(Map<String, Integer> map) {
		return mapper.checkChattingPart(map);
	}
	
	
	
	
	
}
