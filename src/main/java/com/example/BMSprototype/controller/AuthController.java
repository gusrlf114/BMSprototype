package com.example.BMSprototype.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BMSprototype.dto.MemberDto;
import com.example.BMSprototype.dto.loginDto;
import com.example.BMSprototype.jwt.JwtUtil;
import com.example.BMSprototype.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final MemberService ms;
	
	private final JwtUtil jwt;
	
	@PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody MemberDto dto) {
        try {
        	System.out.println("------------------------"+dto.getRole());
            ms.regist(dto);
            return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
        } catch (IllegalStateException e) {
        	
            return ResponseEntity.badRequest().body(
                Map.of("error", e.getMessage()));
        }
    }
	
	@PostMapping("/loginProc")
	public ResponseEntity<Map<String,String>> login(
			@RequestBody loginDto dto,
			HttpServletResponse response){
		 try {
	            String token =ms.login(dto);
	            String role = jwt.getRole(token);
	            response.setHeader("Authorization", "Bearer "+token);
	            return ResponseEntity.ok(Map.of("message", "로그인 성공!","token",token,"role",role));
	        } catch (IllegalStateException e) {
	            return ResponseEntity.badRequest().body(
	                Map.of("error", e.getMessage()));
	        }
	}	 
	
}
