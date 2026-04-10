package com.example.BMSprototype.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BMSprototype.dto.AdminMemberResponse;
import com.example.BMSprototype.dto.MemberDto;
import com.example.BMSprototype.dto.loginDto;
import com.example.BMSprototype.entity.Account;
import com.example.BMSprototype.entity.Member;
import com.example.BMSprototype.entity.Role;
import com.example.BMSprototype.jwt.JwtUtil;
import com.example.BMSprototype.repository.AccountRepository;
import com.example.BMSprototype.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository mr;
	
	private final AccountRepository ar;
	
	private final BCryptPasswordEncoder bpe;
	
	private final JwtUtil jwt;
	
	//랜덤계좌번호 생성
	public String randomAccountNumber() {
		Random r = new Random();
		
		StringBuilder sb =new StringBuilder();
		
		for(int i=0;i<12;i++) {
			sb.append(r.nextInt(10));
		}

		return sb.toString();
	}
	
	//겹치는거없이 계좌생성
	public String uniqueAccountNumber() {
		String an =randomAccountNumber();
		while(ar.existsByAccountNumber(an)) {
			an=randomAccountNumber();
		}
		return an;
	}
	
	
	//dtoToEntity!!
	public Member dtoToEntity(MemberDto dto) {
		Member member = new Member();
		member.setUsername(dto.getUsername());
		member.setPassword(dto.getPassword());
		member.setRole(dto.getRole());
		
		return member;
	}
	
	//회원가입
	public void regist(MemberDto dto) {
		Member a =mr.findByUsername(dto.getUsername());
		
		//아이디 중복검사
		if(a != null) {
			throw new IllegalStateException("아이디가 중복입니다.");
		}
		//비밀번호 암호화
		String newPw =bpe.encode(dto.getPassword());
		Member member =dtoToEntity(dto);
		member.setPassword(newPw);
		
		//등록
		mr.save(member);
		System.out.println("등록완료 !!!!!!!!!!!!!!");
		
		
		//계좌생성 등록
		Account ac =new Account();
		String number =uniqueAccountNumber();
		ac.setAccountNumber(number);
		ac.setBalance(0);
		ac.setMember(member);
		ar.save(ac);
		System.out.println("계좌 생성 완료!!!!!!!!!!!!!!!!!!");
	}
	
	//로그인
	public String login(loginDto dto) {
		Member mem =mr.findByUsername(dto.getUsername());
		//아이디확인
		if(mem == null) {
			throw new IllegalStateException("아이디가 존재하지않습니다.");
		}
		//비번확인
		if(!bpe.matches(dto.getPassword(), mem.getPassword())) {
			throw new IllegalStateException("비밀번호가 일치하지않습니다.");
		}
		
		return jwt.generateToken(dto.getUsername(), mem.getRole().name());
	}
	
	
	//==================admin용=======================
	
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
