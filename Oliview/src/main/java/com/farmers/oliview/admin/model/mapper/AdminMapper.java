package com.farmers.oliview.admin.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.member.model.dto.Member;

@Mapper
public interface AdminMapper {

	// 회원 목록 조회
	List<Member> memberList();

	// 관리자 권한 변경
	int changeAuthority(int memberNo);

}
