package com.farmers.oliview.myPage.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.member.model.dto.Member;

@Mapper
public interface MyPageMapper {

	/** 회원탈퇴
	 * @param loginMember
	 * @return result
	 */
	int secession(Member loginMember);

}
