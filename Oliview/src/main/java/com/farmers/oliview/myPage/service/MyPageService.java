package com.farmers.oliview.myPage.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

	/** 비밀번호 변경
	 * @param memberPw
	 * @param loginMember
	 * @param newPw 
	 * @return
	 */
	int changePw(String memberPw, Member loginMember, String newPw);

	/** 내가 쓴 글 조회
	 * @param loginMember 
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectMyArticleList(Member loginMember, int cp);

	/** 내가 찜한 글 조회
	 * @param loginMember
	 * @param cp
	 * @return
	 */
	Map<String, Object> choiceArticleList(Member loginMember, int cp);

	/** 내가 작성한 댓글 조회
	 * @param loginMember
	 * @param cp
	 * @return
	 */
	Map<String, Object> myCommentList(Member loginMember, int cp);

	/** 댓글 삭제
	 * @param memberNo 
	 * @param commentNoList
	 * @return
	 */
	int deleteComments(int memberNo, List<Integer> commentNoList);


}
