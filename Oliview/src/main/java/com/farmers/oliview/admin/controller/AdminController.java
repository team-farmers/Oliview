package com.farmers.oliview.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.farmers.oliview.admin.model.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
public class AdminController {

	private final AdminService service;
	
	
	@GetMapping("report")
	public String report() {
		return "admin/report";
	}
	
	
	
}
