package com.example.BMSprototype.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BMSprototype.dto.AdminMemberResponse;
import com.example.BMSprototype.entity.Account;
import com.example.BMSprototype.service.AdminService;
import com.example.BMSprototype.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class adminController {
	
	private final AdminService ms;
	
	//아이디 전체조회
	@GetMapping("/members")
	public ResponseEntity<List<AdminMemberResponse>> members(){
		return ResponseEntity.ok(ms.findAll());
	}
	
	
	//상세보기
	@GetMapping("/member")
	public ResponseEntity<List<Account>> member(
			@RequestParam("id") long id){
		return ResponseEntity.ok(ms.findByMemberId(id));
	}
	
	//계좌 동결하기
	@PutMapping("/frozen")
	public ResponseEntity<Map<String,String>> frozen(
			@RequestParam("accountNumber")String accountNumber
			){
		try {
			ms.isActiveFrozen(accountNumber);
			return ResponseEntity.ok(Map.of("message","계좌를 동결시켰습니다."));
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
		}
		
	}
	
	//계좌 동결해제하기
	@PutMapping("/unfrozen")
	public ResponseEntity<Map<String,String>> unfrozen(
			@RequestParam("accountNumber")String accountNumber
			){
		
		try {
			ms.isActiveUnfrozen(accountNumber);
			return ResponseEntity.ok(Map.of("message","계좌를 동결해제시켰습니다."));
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
		}
		
	}
	
	
	
	
}
