package com.farmers.oliview.chatting.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChattingRoom {
	
    private int chattingNo;
    private String lastMessage;
    private String sendTime;
    private int targetNo;
    private String targetNickname;
    private String targetProfile;
    private int notReadCount;
    private int maxMessageNo;
    
    
    private String boardImg; // 같이먹어요 게시글 이미지
    private String boardTitle; // 같이먹어요 게시글 타이틀
    private int maxPeople; // 같이먹어요 참여인원
}
