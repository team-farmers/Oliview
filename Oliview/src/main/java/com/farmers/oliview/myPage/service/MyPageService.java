package com.farmers.oliview.myPage.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.farmers.oliview.member.model.dto.Member;

public interface MyPageService {

	/** 회원탈퇴
	 * @param loginMember
	 * @return result
	 */
	int secession(Member loginMember);

	/** 프로필 이미지 수정
	 * @param profileImg
	 * @param loginMember
	 * @return
	 */
	int profile(MultipartFile profileImg, Member loginMember) throws IllegalStateException, IOException;

	/** 회원정보수정(닉네임)
	 * @param updateMember
	 * @return result
	 */
	int editProfile(Member updateMember);

	/** 회원정보수정(이메일)
	 * @param updateMember
	 * @return result
	 */
	int editInfo(Member updateMember);

}
