package com.farmers.oliview.chatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({"loginMember"})
@Controller
public class ChattingController {
	
	
	@RequestMapping("/together/chat")
	public String chat() {
		return "together/chat";
	}
	

}
