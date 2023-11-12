package com.farmers.oliview.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration // 수동 bean등록 + 설정하기
public class SecurityConfig {

	@Bean 
	public BCryptPasswordEncoder BCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	
	// 로그인 후 이전페이지 돌아가는 정보 저장
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SavedRequestAwareAuthenticationSuccessHandler();
	}
	
	
}
