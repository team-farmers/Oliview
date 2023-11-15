package com.farmers.oliview.admin.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.admin.model.dto.Report;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;

@Mapper
public interface AdminMapper {

	// 회원 목록 조회
	List<Member> memberList();

	// 회원 권한 변경
	int changeAuthority(int memberNo);

	// 회원 탈퇴 복구
	int restoration(int memberNo);

	// 회원 조회
	Member memberInfo(int memberNo);

	// 회원 탈퇴
	int quit(int memberNo);

//	int insertReport(Review review);

	int delete(int reviewNo);

	List<Review> reviewReportList();

	Member reviewReportInfo(int reviewNo);



}