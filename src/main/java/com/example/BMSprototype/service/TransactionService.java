package com.example.BMSprototype.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BMSprototype.entity.Account;
import com.example.BMSprototype.entity.Member;
import com.example.BMSprototype.entity.Transaction;
import com.example.BMSprototype.entity.Transtype;
import com.example.BMSprototype.repository.AccountRepository;
import com.example.BMSprototype.repository.MemberRepository;
import com.example.BMSprototype.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	
	private final MemberRepository mr;
	
	private final AccountRepository ar;
	
	private final TransactionRepository tr;
	
	//동결된계좌인지 확인 
	public void isActive(Account ac) {
		if(!ac.isActive()) {
			throw new RuntimeException("동결된계좌입니다.");
		}
	}
	
	
	
	
	//DEPOSIT(입금)
	@Transactional
	public int deposit(String username,int amount) {
		if(amount < 0) {
			throw new RuntimeException("0이상 입금가능합니다.");
		}
		
		//username으로 member값찾고
		Member member =mr.findByUsername(username);
		
		//member로 계좌 찾고
		Account ac=ar.findByMember(member);
		
		//계좌 동결확인
		isActive(ac);
		
		//입금 돈 넣고
		ac.setBalance(ac.getBalance()+amount);
		
		// 변경
		ar.save(ac);
		
		//거래내역 Transaction
		Transaction tt =Transaction.builder()
					.sender(member)
					.receiver(member)
					.amount(amount)
					.type(Transtype.valueOf("DEPOSIT"))
					.build();
		
		tr.save(tt);
		
		return myBalance(username);
		
	}
	
	//TRANSFER(송금)
	@Transactional
	public void transfer(String username,String accountNumber,int amount) {
		//내계좌 찾고
		Member member =mr.findByUsername(username);
		
		//sender계좌
		Account myac=ar.findByMember(member);
		
		
		//receiver계좌
		Account receiverAc =ar.findByAccountNumber(accountNumber);
		
		//계좌 동결확인
		isActive(myac);
		isActive(receiverAc);
		
		//계좌확인
		if(receiverAc == null) {
			throw new RuntimeException("존재하지않는 계좌번호입니다.");
		}
		//내돈 상대돈
		int myMoney =myac.getBalance();
		int receiverMoney =receiverAc.getBalance();
		
		//잔액확인
		if(myMoney< amount) {
			throw new RuntimeException("잔액이 부족합니다.");
		}
		
		//송금
		myac.setBalance(myMoney-amount);
		receiverAc.setBalance(receiverMoney+amount);
		ar.save(myac);
		ar.save(receiverAc);
		
		//거래내역 Transaction
		Transaction tt =Transaction.builder()
					.sender(member)
					.receiver(receiverAc.getMember())
					.amount(amount)
					.type(Transtype.valueOf("TRANSFER"))
					.build();
		//거래저장
		tr.save(tt);
		
		
	}
	
	
	//최근 거래 내역(최근 5건)
	public List<Transaction> read(String username){
		Member member =mr.findByUsername(username);
				
		//내계좌
		Account myac=ar.findByMember(member);
		
		return tr.findTop5BySenderOrReceiverOrderByCreatedAtDesc(member, member);
	}
	
	//내계좌 잔액
	public int myBalance(String username) {
		Member member =mr.findByUsername(username);
		
		//내계좌
		Account myac=ar.findByMember(member);
		myac.getBalance();
		
		return myac.getBalance();
	}
	
	
}
