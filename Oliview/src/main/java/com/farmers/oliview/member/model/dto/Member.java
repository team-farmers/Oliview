package com.farmers.oliview.member.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberEmail;
	private String memberName;
	private String memberNickname;
	private String memberProfile;
	private String memberEnrollDate;
	private String authority;
	private String agreeChoice;
	private String memberDelFl;
	
}
