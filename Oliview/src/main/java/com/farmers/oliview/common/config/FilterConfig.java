package com.farmers.oliview.common.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.farmers.oliview.common.filter.AdminFilter;
import com.farmers.oliview.common.filter.LoginFilter;
import com.farmers.oliview.common.filter.LogoutFilter;

@Configuration
public class FilterConfig {

	/**
	 * 로그인 필터 세팅
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter() {

		FilterRegistrationBean<LoginFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new LoginFilter()); // 사용할 로그인 필터 객체 추가

		// 로그인 전) 마이페이지, 리뷰/같이먹어요 글쓰기/수정/채팅 못들어가게 필터링함

		String[] filteringUrl = { "/myPage/*", "/together/boardUpdate", "/together/posting",
				"/review/insert", "/review/update", "/chatting/chat", "/chatting/talk" };
		
		// 필터링할 주소 지정
		filter.setUrlPatterns(Arrays.asList(filteringUrl));
		filter.setName("loginFilter");
		filter.setOrder(1);

		return filter;
	}

	
	/** 로그아웃 필터 세팅
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<LogoutFilter> logoutFilter(){
	 
		FilterRegistrationBean<LogoutFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new LogoutFilter()); // 사용할 로그인 필터 객체 추가
		 
		// 로그인 후) 로그인, 회원가입 페이지, 비밀번호 찾기, 아이디찾기 못들어가게 필터링 함
		 
		String[] filteringUrl = { "/member/signup", "/member/id-find-confirm",
				"/member/id-find","/member/login","/member/pw-find-confirm","/member/pw-find",
				"/member/signup-fin","/member/signup-info"};
		
		// 필터링할 주소 지정
		filter.setUrlPatterns(Arrays.asList(filteringUrl));
		
		filter.setName("logoutFilter");
		filter.setOrder(1);

		return filter;
	}
	
	
	/** 관리자권한 필터 세팅
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<AdminFilter> adminFilter(){
		 
		FilterRegistrationBean<AdminFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new AdminFilter()); // 사용할 로그인 필터 객체 추가
		 
		// 로그인 후) 로그인, 회원가입 페이지, 비밀번호 찾기, 아이디찾기 못들어가게 필터링 함
		List<String> filteringUrl = new ArrayList<>();
		 
		filteringUrl.add("/admin/*");
		filter.setUrlPatterns(filteringUrl);
		filter.setName("adminFilter");
		filter.setOrder(2);

		return filter;
	}
	
	
	
	
	 

}
