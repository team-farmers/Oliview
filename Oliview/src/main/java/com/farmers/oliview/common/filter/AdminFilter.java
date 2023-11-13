package com.farmers.oliview.common.filter;

import java.io.IOException;

import com.farmers.oliview.member.model.dto.Member;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		// session 객체 얻어오기
		HttpSession session = req.getSession();
		
		// Object -> Member로 다운캐스팅
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		// 로그인 X 거나 또는 관리자 권한이 없을 경우
		if(loginMember == null || loginMember.getAuthority().equals("N")) {
			
			resp.sendRedirect("/adminError");
			
		} else { // 관리자권한 O
			chain.doFilter(request, response);
		} 
	}
	
	
}
