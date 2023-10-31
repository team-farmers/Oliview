package com.farmers.oliview.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmers.oliview.email.model.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("email")
@RequiredArgsConstructor
public class EmailController {
	
	private final EmailService service; 
	
	/** 회원가입 인증번호 생성 + 이메일 발송 + insert 또는 update
	 * @param email
	 * @return
	 */
	@PostMapping("signup")
	public int signup(@RequestBody String email) {
		return service.sendEmail("signup", email);
	}	
	
	
	/** 인증번호 확인
	 * @param paramMap
	 * @return
	 */
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, Object> paramMap) {
		return service.checkAuthKey(paramMap);
	}
	
	

}
