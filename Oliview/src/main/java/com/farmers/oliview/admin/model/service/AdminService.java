package com.farmers.oliview.admin.model.service;

import java.util.List;

import com.farmers.oliview.member.model.dto.Member;

public interface AdminService {

	// 회원 목록 조회
	List<Member> memberList();

}
