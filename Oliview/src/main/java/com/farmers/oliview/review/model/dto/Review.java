package com.farmers.oliview.review.model.dto;

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
	private String reviewOneLine;
	private String reviewImg;
	private String reviewFood;
	private String writeDate;
	private int readCount;
	private String reviewDelFl;
	private int memberNo;

}
