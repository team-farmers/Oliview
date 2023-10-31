package com.farmers.oliview.member.service;

import com.farmers.oliview.member.model.dto.Member;

public interface MemberService{

	/** 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	Member login(Member inputMember);

	/** 이메일 중복 검사
	 * @param email
	 * @return
	 */
	int checkEmail(String email);

}
