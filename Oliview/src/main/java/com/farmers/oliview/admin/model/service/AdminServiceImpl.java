package com.farmers.oliview.admin.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.admin.model.mapper.AdminMapper;
import com.farmers.oliview.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	
	public final AdminMapper mapper;

	// 회원 목록 조회
	@Override
	public List<Member> memberList() {
		return mapper.memberList();
	}

	// 권한 변경
	@Override
	public int changeAuthority(int memberNo) {
		return mapper.changeAuthority(memberNo);
	}
}
