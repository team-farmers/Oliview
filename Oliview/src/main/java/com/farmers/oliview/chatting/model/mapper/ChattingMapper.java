package com.farmers.oliview.chatting.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.chatting.model.dto.ChattingRoom;
import com.farmers.oliview.chatting.model.dto.Message;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.together.dto.Together;

@Mapper
public interface ChattingMapper {

	/** 채팅 목록 조회
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
	 * @return
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
	 * @param parseInt
	 * @return
	 */
	List<Message> selectMessageList(int parseInt);

	/** 메세지 DB 입력
	 * @param msg
	 * @return
	 */
	int insertMessage(Message msg);

	/** 채팅 참여하는 같이먹어요 게시글 정보 조회
	 * @param boardNo
	 * @return
	 */
	Together talkTogether(int boardNo);

	/** 채팅방 개설자 회원번호 조회
	 * @param map
	 * @return
	 */
	int searchOpenMember(Map<String, Integer> map);

	/** 채팅방 목록에서 클릭 후 -> 채팅방이 존재하는지 여부 확인
	 * @param map
	 * @return
	 */
	int checkChattingPart(Map<String, Integer> map);

}
