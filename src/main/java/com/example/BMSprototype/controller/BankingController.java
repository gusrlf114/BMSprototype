package com.example.BMSprototype.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BMSprototype.dto.ReadAccountDto;
import com.example.BMSprototype.dto.TransferDto;
import com.example.BMSprototype.entity.Transaction;
import com.example.BMSprototype.service.MemberService;
import com.example.BMSprototype.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/banking")
public class BankingController {
	
	private final MemberService ms;
	
	private final TransactionService ts;
	
	
	@PostMapping("/deposit")
	public ResponseEntity<Map<String,String>> deposit(
			Authentication auth,
			@RequestParam("amount") int amount
			){
		
		try {
			String username =auth.getName();
			
			int bal = ts.deposit(username, amount);
			String balance =String.valueOf(bal);
			return ResponseEntity.ok(
					Map.of("message","입금을 완료했습니다.",
							"balance",balance));
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest()
					.body(Map.of("error",e.getMessage()));
		}
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<Map<String,String>> transfer(
			Authentication auth,
			@RequestBody TransferDto dto
			){
		System.out.println(dto);
		
		
		try {
			String username=auth.getName();
			String accountNumber=dto.getAccountNumber();
			int amount =dto.getAmount();
			//sysout ========
			System.out.println(username);
			//===================
			ts.transfer(username, accountNumber, amount);
			return ResponseEntity.ok(Map.of(
					"message","송금을 완료했습니다."));
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest()
				.body(Map.of("error",e.getMessage()));
		}
	}
	
	@PostMapping("/account")
	public ResponseEntity<ReadAccountDto> account(
			Authentication auth){
		String username=auth.getName();
		
		
		List<Transaction> recent =ts.read(username);
		int myBalance =ts.myBalance(username);
		
		System.out.println(recent.get(0));
		ReadAccountDto dto =new ReadAccountDto();
		dto.setList(recent);
		dto.setMyBalance(myBalance);
		return ResponseEntity.ok(dto);
	}
	
	
	
	
}
