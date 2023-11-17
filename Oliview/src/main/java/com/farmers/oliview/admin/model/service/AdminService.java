package com.farmers.oliview.admin.model.service;

import java.util.List;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.dto.ReviewReport;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.dto.TogetherReport;

public interface AdminService {

	// 회원 목록조회
	List<Member> memberList();

	// 회원 복구
	int restoration(int memberNo);

	// 회원 조회
	Member memberInfo(int memberNo);

	// 회원 탈퇴
	int quit(int memberNo);

	int delete(int reviewNo);

	List<Review> reviewReportList();

	

	int user(int memberNo);

	int admin(int memberNo);

	List<Together> togetherList();

	int deleteTogether(int boardNo);

	List<ReviewReport> reportlist();


	





	

}