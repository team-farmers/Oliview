package com.farmers.oliview.member.service;

import com.farmers.oliview.member.model.dto.Member;

public interface MemberService{

	/** 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	Member login(Member inputMember);
	
	/** 아이디 중복 검사
	 * @param id
	 * @return
	 */
	int checkId(String id);

	/** 이메일 중복 검사
	 * @param email
	 * @return
	 */
	int checkEmail(String email);

	/** 닉네임 중복 검사
	 * @param nickname
	 * @return result
	 */
	int checkNickname(String nickname);

	/** 회원가입
	 * @param inputMember
	 * @return result
	 */
	int signup(Member inputMember);

	/** 멤버 찾기
	 * @param inputMember
	 * @return
	 */
	int memberFind(Member inputMember);

	/** 아이디 찾기
	 * @param inputMember
	 * @return
	 */
	String idFind(Member inputMember);


}
