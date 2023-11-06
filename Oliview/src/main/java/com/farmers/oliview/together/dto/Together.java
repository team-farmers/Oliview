package com.farmers.oliview.together.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Together {
	
	private int boardNo; // 게시글 번호
	private String boardTitle; // 제목
	private int numberPeople; // 참여인원
	private int dateCreated; // 작성일
	
	private String boardImg; // 이미지
	private String boardContent; // 게시글 내용
	private String boardDelFl; // 삭제여부
	private String boardSubtitle; //부제목
	
	private String MemberNo; // 회원번호
	private int MettingDate; // 모임날짜

	

	
	
		
	
	
	
	
}
