package com.farmers.oliview.chatting.model.service;

import java.util.List;
import java.util.Map;

import com.farmers.oliview.chatting.model.dto.ChattingRoom;
import com.farmers.oliview.chatting.model.dto.Message;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.together.dto.Together;

public interface ChattingService {

	/** 채팅방 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<ChattingRoom> selectRoomList(int memberNo);

	/** 채팅 상대 검색
	 * @param map
	 * @return
	 */
	List<Member> selectTarget(Map<String, Object> map);

	/** 채팅방 존재유무확인
	 * @param map
	 * @return CHATTING_NO
	 */
	int checkChattingNo(Map<String, Integer> map);

	/** 채팅방 생성
	 * @param map
	 * @return
	 */
	int createChattingRoom(Map<String, Integer> map);

	/** 채팅 읽음 표시
	 * @param paramMap
	 * @return
	 */
	int updateReadFlag(Map<String, Object> paramMap);

	/** 채팅 불러오기
	 * @param paramMap
	 * @return
	 */
	List<Message> selectMessageList(Map<String, Object> paramMap);
	
	/** 입력된 메세지 DB 저장
	 * @param msg
	 * @return
	 */
	int insertMessage(Message msg);

	/** 채팅 참여하는 같이먹어요 게시글 정보 조회
	 * @param boardNo
	 * @return
	 */
	Together talkTogether(int boardNo);

}
