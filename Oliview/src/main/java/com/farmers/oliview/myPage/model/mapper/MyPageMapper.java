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

	/** 프로필 이미지 수정
	 * @param loginMember
	 * @return
	 */
	int profile(Member loginMember);

	/** 회원정보수정(닉네임)
	 * @param updateMember
	 * @return
	 */
	int editProfile(Member updateMember);

	/** 회원정보수정(이메일)
	 * @param updateMember
	 * @return
	 */
	int editInfo(Member updateMember);

}
