package com.farmers.oliview.myPage.service;

import com.farmers.oliview.member.model.dto.Member;

public interface MyPageService {

	/** 회원탈퇴
	 * @param loginMember
	 * @return result
	 */
	int secession(Member loginMember);

}
