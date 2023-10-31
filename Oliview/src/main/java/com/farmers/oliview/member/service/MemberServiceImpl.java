package com.farmers.oliview.member.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	
	private final MemberMapper mapper;
	private final BCryptPasswordEncoder bcrypt;
	
	/** 로그인 서비스
	 *
	 */
	@Override
	public Member login(Member inputMember) {
			
			// 아이디가 일치하는 회원의 암호화된 비밀번호 얻어오기
			Member loginMember = mapper.login(inputMember);
			
			// 조회결과가 없을 경우(아이디 일치 회원 없음) return null
			if(loginMember == null) {
				return null;
			}
			
//			if( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
//				return null;
//			}
			
			// 비밀번호 일치시 비번 제거 후 loginMember return
			loginMember.setMemberPw(null);
			return loginMember;
	}
	
	
	/** 이메일 중복 검사
	 *
	 */
	@Override
	public int checkEmail(String email) {
		return mapper.checkEmail(email);
	}

	
	

}
