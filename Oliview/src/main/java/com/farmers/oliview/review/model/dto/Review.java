package com.farmers.oliview.review.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
	
	// review 테이블과 매핑되는 필드
	private int reviewNo;
	private String reviewTitle;
	private String reviewAddress;
	private double starTaste;
	private double starAmount;
	private double starClean;
	private double starTotal;
	private String reviewOneLine;
	private String reviewImg;
	private String reviewMenu;
	private String writeDate;
	private int readCount;
	private String reviewDelFl;
	private int memberNo;
	
	// 리뷰 상세 조회 (reviewDetail) 시 매핑되는 필드
	private String memberNickname; // 작성자 닉네임
	private String memberProfile; // 프로필 이미지 경로
	
    private int commentCount; //	 댓글 수
    private int likeCount; // 좋아요 수
	
	
    // 댓글 목록을 저장할 필드
    private List<Comment> commentList;
    

    // 메인페이지에서 리뷰 목록 불러올때 사용할 URL 필드
    private String url;

    // 가게평점
    private double storeStarTotal;


}
