package com.example.BMSprototype.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.BMSprototype.dto.MemberDto;
import com.example.BMSprototype.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MemberService ms;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String loginpage() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signupPage() {
		return "signup";
	}
	
	@GetMapping("/userpage")
	public String userpage() {
		return "/user/userpage";
	}
	
	@GetMapping("/adminpage")
	public String adminpage() {
		return "/admin/adminpage";
	}
	
	@GetMapping("/memberdetail")
	public String memberDetail() {
		return "/admin/memberdetail";
	}
	
	
}
