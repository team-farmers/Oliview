package com.farmers.oliview.member.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.member.controller.MemberController;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
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
	
	
	
	/** 회원가입
	 *
	 */
	@Override
	public int signup(Member inputMember) {
		
		// 비밀번호 암호화
		inputMember.setMemberPw( bcrypt.encode(inputMember.getMemberPw()) );
		
		log.debug(inputMember.toString());
		
		return mapper.signup(inputMember);
	}
	
	
	/** 아이디 중복 검사
	 *
	 */
	@Override
	public int checkId(String id) {
		return mapper.checkId(id);
	}
	
	
	
	/** 이메일 중복 검사
	 *
	 */
	@Override
	public int checkEmail(String email) {
		return mapper.checkEmail(email);
	}
	
	/** 닉네임 중복 검사
	 *
	 */
	@Override
	public int checkNickname(String nickname) {
		return mapper.checkNickname(nickname);
	}

	
	/** 멤버 일치 확인 (id)
	 *
	 */
	@Override
	public int memberFind(Member inputMember) {
		return mapper.memberFind(inputMember);
	}
	
	/** 아이디 찾기
	 *
	 */
	@Override
	public String idFind(Member inputMember) {
		return mapper.idFind(inputMember);
	}
	
	
	/** 멤버 찾기(pw)
	 *
	 */
	@Override
	public int memberPwFind(Member inputMember) {
		return mapper.memberPwFind(inputMember);
	}
	
	/** 비밀번호 찾기
	 *
	 */
	@Override
	public Member pwFind(Member inputMember) {
		return mapper.pwFind(inputMember);
	}
	
	
	/** 비밀번호 변경
	 *
	 */
	@Override
	public int changePw(Member inputMember) {
		
		// 비밀번호 암호화
		inputMember.setMemberPw( bcrypt.encode(inputMember.getMemberPw()) );
		
		return mapper.changePw(inputMember);
	}
	

}
