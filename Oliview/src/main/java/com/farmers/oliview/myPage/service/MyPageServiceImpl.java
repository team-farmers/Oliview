package com.farmers.oliview.myPage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.myPage.model.mapper.MyPageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageServiceImpl implements MyPageService{
	
	private final MyPageMapper mapper;
	
	/** 회원탈퇴
	 *
	 */
	@Override
	public int secession(Member loginMember) {
		return mapper.secession(loginMember);
	}
	
	

}
