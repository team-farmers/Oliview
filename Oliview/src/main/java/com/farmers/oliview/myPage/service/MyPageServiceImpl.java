package com.farmers.oliview.myPage.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.farmers.oliview.common.utility.Util;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.myPage.model.mapper.MyPageMapper;
import com.farmers.oliview.review.model.dto.Pagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	

	/** 비밀번호 변경
	 *
	 */
	@Override
	public int changePw(String memberPw, Member loginMember, String newPw) {
		
		// 로그인 멤버의 암호화된 비밀번호 가져오기
		String encPw = mapper.findPw(loginMember);
		
		// 비밀번호 불일치시 리턴
		if (!bcrypt.matches(memberPw, encPw)) {
			return 0;
		}
		
		// 비밀번호 일치시 새 비밀번호로 수정
		loginMember.setMemberPw(bcrypt.encode(newPw));
		
		return mapper.changePw(loginMember);
	}
	
	
	
	/** 내가 쓴 글 조회
	 *
	 */
	@Override
	public Map<String, Object> selectMyArticleList(Member loginMember, int cp) {
		
		// 로그인 된 회원이 작성한 리뷰, 같이먹어요 게시글 수 얻어오기
		int listCount = mapper.getListCount(loginMember);
		
		/* cp, listCount를 이용해서 Pagination 객체 생성 */
		Pagination pagination = new Pagination(cp, listCount);
		
		// RowBounds 객체 생성
		int offset = (pagination.getCurrentPage()-1) * pagination.getLimit();
		
		int limit = pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		// 마이바티스 호출
		List<Map<String, Object>> boardList = mapper.selectMyArticleList(loginMember.getMemberNo(), rowBounds);
		
		// Map에 담아서 반환
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		
		return map;
	}
	
	
	
	/** 내가 찜한 글 조회
	 *
	 */
	@Override
	public Map<String, Object> choiceArticleList(Member loginMember, int cp) {
		
		// 로그인 된 회원의 찜한글 수 얻어오기
		int listCount = mapper.getChoiceListCount(loginMember);
		
		/* cp, listCount를 이용해서 Pagination 객체 생성 */
		Pagination pagination = new Pagination(cp, listCount);
		
		// RowBounds 객체 생성
		int offset = (pagination.getCurrentPage()-1) * pagination.getLimit();
		
		int limit = pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		// 마이바티스 호출
		List<Map<String, Object>> boardList = mapper.choiceArticleList(loginMember.getMemberNo(), rowBounds);
		
		// Map에 담아서 반환
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("boardList", boardList);

		return map;
	}
	
	
	
	

}
