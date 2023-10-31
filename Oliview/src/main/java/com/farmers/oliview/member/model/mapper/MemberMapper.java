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

	/** 이메일 중복 검사
	 * @param email
	 * @return
	 */
	int checkEmail(String email);




}
