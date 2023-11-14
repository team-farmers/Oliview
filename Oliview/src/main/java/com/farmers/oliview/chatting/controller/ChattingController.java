package com.farmers.oliview.chatting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.farmers.oliview.chatting.model.dto.ChattingRoom;
import com.farmers.oliview.chatting.model.dto.Message;
import com.farmers.oliview.chatting.model.service.ChattingService;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.together.dto.Together;

import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
public class ChattingController {
	
	private final ChattingService service;
	
	/** 채팅목록 포워드
	 * @return
	 */
	@RequestMapping("/chatting/chat")
	public String chat(@SessionAttribute("loginMember") Member loginMember, Model model) {
		
		List<ChattingRoom> roomList = service.selectRoomList(loginMember.getMemberNo());
        model.addAttribute("roomList", roomList);
        
		return "chatting/chat";
	}
	
	
	/** 채팅페이지 포워드
	 * @param boardNo : 같이먹어요에서 넘어온 게시글 번호(채팅방 번호로 사용 예정)
	 * @param model
	 * @return
	 */
	@RequestMapping("/chatting/talk/{boardNo:[0-9]+}")
	public String talk( @PathVariable("boardNo") int boardNo, Model model) {
		
		// 같이먹어요 게시글 정보 조회 후 넘기기
		Together together = service.talkTogether(boardNo);
		
		model.addAttribute("together", together);
		
		return "chatting/talk";
	}
	
    
    /** 채팅 상대 검색
     * @param query
     * @param loginMember
     * @return
     */
    @GetMapping(value="/chatting/selectTarget", produces="application/json; charset=UTF-8")
    @ResponseBody
    public List<Member> selectTarget(String query, @SessionAttribute("loginMember") Member loginMember){
    	
    	Map<String, Object> map = new HashMap<>();
    	
    	map.put("memberNo", loginMember.getMemberNo());
    	map.put("query", query);
    	
    	return service.selectTarget(map);
    }
	
    
    /** 채팅방 입장(없으면 생성)
     * @param selectChattingNo : 채팅방번호(같이먹어요 게시글 번호)
     * @param loginMember : (채팅 신규 참여자)
     * @return chattingNo : 채팅방 번호(게시글 번호와 같음)
     */
    @GetMapping("/chatting/enter")
    @ResponseBody
    public int chattingEnter(int selectChattingNo, @SessionAttribute("loginMember") Member loginMember) {
     
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        map.put("selectChattingNo", selectChattingNo);
        map.put("loginMemberNo", loginMember.getMemberNo());
        
        // 로그인 한 회원이 해당 게시글의 채팅에 참여한 기록이 있는지 확인
        int chattingNo = service.checkChattingNo(map);
        
        // 참여 기록이 없다면 채팅방 입장
        if(chattingNo == 0) {
            chattingNo = service.createChattingRoom(map);
        }
        
        return chattingNo;
    }
    
    
    
    
    
    
    
    /** 채팅방 목록 조회
     * @param loginMember
     * @return
     */
    @GetMapping(value="/chatting/roomList", produces="application/json; charset=UTF-8")
    @ResponseBody
    public List<ChattingRoom> selectRoomList(@SessionAttribute("loginMember") Member loginMember) {
    	return service.selectRoomList(loginMember.getMemberNo());
    }
    
	
    /** 채팅 읽음 표시
     * @param paramMap
     * @return
     */
    @PutMapping("/chatting/updateReadFlag")
    @ResponseBody
    public int updateReadFlag(@RequestBody Map<String, Object> paramMap) {
        return service.updateReadFlag(paramMap);
    }
    
    
    /** 채팅 불러오기
     * @param paramMap : chattingNo , memberNo 
     * @return messageList : 채팅 메세지 리스트
     */
    @GetMapping(value="/chatting/selectMessage", produces="application/json; charset=UTF-8")
    @ResponseBody
    public List<Message> selectMessageList(@RequestParam Map<String, Object> paramMap) {
        return service.selectMessageList(paramMap);
    }
    
}
