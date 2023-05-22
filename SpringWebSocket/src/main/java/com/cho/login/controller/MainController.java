package com.cho.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	
	@GetMapping("/")
	public String login() {
		return "main";
	}
	@GetMapping("/chat")
	public String chat() {
		return "chat";
	}
}