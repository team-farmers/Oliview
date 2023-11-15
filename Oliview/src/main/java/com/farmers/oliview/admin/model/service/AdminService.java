package com.farmers.oliview.admin.model.service;

import java.util.List;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.together.dto.Together;

public interface AdminService {

	// 회원 목록조회
	List<Member> memberList();

	// 관리자 권한 변경
	int changeAuthority(int memberNo);

	// 회원 복구
	int restoration(int memberNo);

	// 회원 조회
	Member memberInfo(int memberNo);

	// 회원 탈퇴
	int quit(int memberNo);

	int delete(int reviewNo);

	List<Review> reviewReportList();

	Member reviewReportInfo(int reviewNo);

	List<Together> togetherList();

	int deletReview(int reviewNo);

	int returnReview(int reviewNo);



	

}