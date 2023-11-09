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

}
