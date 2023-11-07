package com.farmers.oliview.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/** 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	Member login(Member inputMember);
	
	/** 회원가입
	 * @param inputMember
	 * @return
	 */
	int signup(Member inputMember);
	
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
	 * @return
	 */
	int checkNickname(String nickname);

	/** 멤버 일치 확인(id)
	 * @param inputMember
	 * @return
	 */
	int memberFind(Member inputMember);

	/** 아이디 찾기
	 * @param inputMember
	 * @return
	 */
	String idFind(Member inputMember);

	/** 멤버 일치 확인(pw)
	 * @param inputMember
	 * @return
	 */
	int memberPwFind(Member inputMember);

	/** 비밀번호 찾기
	 * @param inputMember
	 * @return
	 */
	Member pwFind(Member inputMember);

	/** 비밀번호 변경
	 * @param inputMember
	 * @return
	 */
	int changePw(Member inputMember);









}
