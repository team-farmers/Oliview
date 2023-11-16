package com.farmers.oliview.admin.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.admin.model.mapper.AdminMapper;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.together.dto.Together;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	public final AdminMapper mapper;

	// 회원 목록 조회
	@Override
	public List<Member> memberList() {
		return mapper.memberList();
	}

	// 권한 변경
	@Override
	public int user(int memberNo) {
		return mapper.user(memberNo);
	}

	// 회원 복구
	@Override
	public int restoration(int memberNo) {
		return mapper.restoration(memberNo);
	}

	// 회원 조회
	@Override
	public Member memberInfo(int memberNo) {
		return mapper.memberInfo(memberNo);
	}

	@Override
	public int quit(int memberNo) {
		return mapper.quit(memberNo);
	}

	@Override
	public List<Review> reviewReportList() {
		return mapper.reviewReportList();
	}

	@Override
	public int delete(int reviewNo) {
		return mapper.delete(reviewNo);
	}

	@Override
	public Member reviewReportInfo(int reviewNo) {
		return mapper.reviewReportInfo(reviewNo);
	}

	@Override
	public List<Together> togetherList() {
		return mapper.togetherList();
	}
	
	@Override
	public int admin(int memberNo) {
		return mapper.admin(memberNo);
	}

}