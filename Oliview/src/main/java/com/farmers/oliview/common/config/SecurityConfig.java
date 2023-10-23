package com.farmers.oliview.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 수동 been등록 + 설정하기
public class SecurityConfig {

	@Bean 
	public BCryptPasswordEncoder BCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}
}
