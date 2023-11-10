package com.farmers.oliview.review.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comment {
	
	private int commentNo;
    private String commentContent;
    private String commentWriteDate;
    private String commentDelFl;
    private int reviewNo;
    private int memberNo;
    private int parentNo; 
    
    private String memberNickname;
    private String memberProfile;
    
    // 마이페이지_내가 작성한 댓글에 필요한 필드 추가
    private String reviewTitle; // 댓글 작성된 게시글 제목
    private String url; // 댓글 작성된 게시글 url
    private int commentCount; // 한 게시글에 작성된 총 댓글의 갯수
    
    

}
