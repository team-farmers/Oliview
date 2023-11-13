package com.farmers.oliview.common.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		// session 객체 얻어오기
		HttpSession session = req.getSession();
		
		//로그인 상태가 아닌경우
		if(session.getAttribute("loginMember") == null) {
			// /loginError리다이렉트
			resp.sendRedirect("/loginError");
			
		} else { // 로그인 O
			// 다음 필터 or 디스패치서블릿 연결
			chain.doFilter(request, response);
		}
		
	}
	
	
}
