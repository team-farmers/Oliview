package com.farmers.oliview.myPage.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.farmers.oliview.common.utility.Util;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.myPage.model.mapper.MyPageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService{
	
	private final MyPageMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	@Value("${my.member.webpath}")
	private String webpath;
	
	@Value("${my.member.location}")
	private String folderPath;
	
	
	/** 회원탈퇴
	 *
	 */
	@Override
	public int secession(Member loginMember) {
		return mapper.secession(loginMember);
	}
	
	
	/** 프로필 이미지 수정
	 * @throws IOException 
	 * @throws IllegalStateException 
	 *
	 */
	@Override
	public int profile(MultipartFile profileImg, Member loginMember) throws IllegalStateException, IOException {
		
		// 이전 프로필 백업
		String backup = loginMember.getMemberProfile();
		
		// 변경 파일명 저장 변수
		String rename = null;
		
		if(profileImg.getSize() > 0) { // 업로드된 파일이 있다면
			
			// 파일명 변경
			rename = Util.fileRename(profileImg.getOriginalFilename());
			
			// 바뀐 파일명 + 경로를 loginMember 세팅
			//loginMember.setProfileImg( 경로 + rename ); // 경로는 config.properties에서 얻어옴
			loginMember.setMemberProfile( webpath + rename );
			
		} else { // 업로드된 파일이 없다면 -> 기본이미지로 변경
			loginMember.setMemberProfile(null); // 이미지 제거
		}
		
		int result = mapper.profile(loginMember);
		
		// DB 업뎃 성공 -> 메모리에 임시저장된 파일을 지정된 경로에 저장
		if(result > 0) {
			
			if(profileImg.getSize() > 0) { // 업로드 파일이 있을 때
				
				// 파일.transferTo(파일경로)
				// -> 메모리에 저장된 파일을 지정된 경로로 옮김(저장)
				profileImg.transferTo(new File(folderPath + rename));
			}
			
		} else { // DB 업뎃 실패
			// 백업 세팅
			loginMember.setMemberProfile(backup);
		}
		
		return result;
	}
	
	/** 회원정보수정(닉네임)
	 *
	 */
	@Override
	public int editProfile(Member updateMember) {
		return mapper.editProfile(updateMember);
	}
	
	
	/** 회원정보수정(이메일)
	 *
	 */
	@Override
	public int editInfo(Member updateMember) {
		return mapper.editInfo(updateMember);
	}
	

	
	

}
