package com.example.BMSprototype.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BMSprototype.dto.AdminMemberResponse;
import com.example.BMSprototype.entity.Account;
import com.example.BMSprototype.entity.Member;
import com.example.BMSprototype.jwt.JwtUtil;
import com.example.BMSprototype.repository.AccountRepository;
import com.example.BMSprototype.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final MemberRepository mr;
	
	private final AccountRepository ar;
	
	//전체 조회하기
	public List<AdminMemberResponse> findAll(){
		List<Member> a=mr.findAll();
		
		
		List<AdminMemberResponse> list=a.stream().map(
				member-> {
					AdminMemberResponse dto =new AdminMemberResponse();
					dto.setId(member.getId());
					dto.setRole(String.valueOf(member.getRole()));
					dto.setUsername(member.getUsername());
					return dto;
				})
				.collect(Collectors.toList());
		
		
		
		return list;
	}
	
	//상세보기
	public List<Account> findByMemberId(long id){
		return ar.findByMember_Id(id);
	}
	
	//계좌 동결하기 
	public void isActiveFrozen(String accountNumber) {
		//계좌 찾고
		Account a =ar.findByAccountNumber(accountNumber);
		
		if(a == null) {
			throw new RuntimeException("계좌번호가 존재하지않습니다.");
		}
		
		if(!a.isActive()) {
			throw new RuntimeException("이미 동결된 계좌입니다.");
		}
		
		
		a.setActive(false);
		ar.save(a);
	}
	
	//계좌 동결하기 
		public void isActiveUnfrozen(String accountNumber) {
			//계좌 찾고
			Account a =ar.findByAccountNumber(accountNumber);
			
			if(a == null) {
				throw new RuntimeException("계좌번호가 존재하지않습니다.");
			}
			
			if(a.isActive()) {
				throw new RuntimeException("이미 동결되지않은 계좌입니다.");
			}
			
			
			a.setActive(true);
			ar.save(a);
		}
}
