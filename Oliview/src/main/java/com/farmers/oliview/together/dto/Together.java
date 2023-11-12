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
	private String dateCreated; // 작성일
	
	private String boardImg; // 이미지
	private String boardContent; // 게시글 내용
	private String boardDelFl; // 삭제여부
	private String boardSubtitle; //부제목
	
	private int memberNo; // 회원번호
	private String mettingDate; // 모임날짜
    private String maxPeople;
	private int currentPeople;
	
    private int readCount;
   

	//-------------------------------------
	
	// 게시글 이미지 목록
	private List<BoardImg> imageList;

	
	// 목록조회, 상세조회시 매핑되는 필드
	private int commentCount;
	private String memberNickname;
	private String thumbnauil;
	
	
	// 메인페이지 최신게시물 불러올때 사용되는 url
	private String url;
	
	
	
	
}
