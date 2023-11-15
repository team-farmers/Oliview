package com.farmers.oliview.admin.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

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

	Member memberInfo(int memberNo);

	




}
