package com.farmers.oliview.admin.model.service;

import java.util.List;
import java.util.Map;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;

public interface AdminService {

	// 회원 정보 
	Review memberInfo(Map<String, Object> map);
	
	// 회원 목록 조회
	List<Member> memberList();

	// 관리자 권한 변경
	int changeAuthority(int memberNo);

	// 회원 복구
	int restoration(int memberNo);


}
