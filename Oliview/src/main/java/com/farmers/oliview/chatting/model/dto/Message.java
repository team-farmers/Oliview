package com.farmers.oliview.chatting.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {
    private int messageNo;
    private String messageContent;
    private String readFl;
    private int senderNo;
    private int chattingNo;
    private int targetNo;
    private String sendTime;
    
    
    // 메세지 작성자 프로필 이미지
    private String senderProfile;
    
    // 메세지 작성자 닉네임
    private String senderNickname;
}
