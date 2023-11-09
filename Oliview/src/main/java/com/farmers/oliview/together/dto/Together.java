package com.farmers.oliview.together.dto;

import java.util.List;


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
	
	private int memberNo; // 회원번호
	private int MettingDate; // 모임날짜
	private int boardCode;
	
    private int readCount;
   

	//-------------------------------------
	
	// 게시글 이미지 목록
	private List<BoardImg> imageList;

	
	// 목록조회, 상세조회시 매핑되는 필드
	private int commentCount;
	private String memberNickname;
	private String thumbnauil;
	
	
		
	
	
	
	
}
