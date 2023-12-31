package com.farmers.oliview.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Comment;

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

	/** 기존 암호화된 비밀번호 가져오기
	 * @param loginMember
	 * @return
	 */
	String findPw(Member loginMember);

	/** 비밀번호 변경
	 * @param loginMember
	 * @return
	 */
	int changePw(Member loginMember);

	/** 내가 쓴 글 게시글 수 확인
	 * @param loginMember
	 * @return
	 */
	int getListCount(Member loginMember);

	/** 내가 쓴 글 목록 불러오기
	 * @param memberNo 
	 * @param rowBounds
	 * @return
	 */
	List<Map<String, Object>> selectMyArticleList(int memberNo, RowBounds rowBounds);

	/** 찜한글 게시글 수 확인
	 * @param loginMember
	 * @return
	 */
	int getChoiceListCount(Member loginMember);

	/** 찜한 글 게시글 목록 불러오기
	 * @param memberNo
	 * @param rowBounds
	 * @return
	 */
	List<Map<String, Object>> choiceArticleList(int memberNo, RowBounds rowBounds);

	/** 내가 작성한 댓글 수 확인
	 * @param loginMember
	 * @return
	 */
	int getMyCommentListCount(Member loginMember);

	/** 내가 작성한 댓글 목록 불러오기
	 * @param memberNo
	 * @param rowBounds
	 * @return
	 */
	List<Comment> myCommentList(int memberNo, RowBounds rowBounds);

	/** 댓글 삭제 기능
	 * @param map
	 * @return
	 */
	int deleteComments(Map<String, Object> map);

}
