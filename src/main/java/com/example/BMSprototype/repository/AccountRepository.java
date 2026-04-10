package com.example.BMSprototype.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BMSprototype.entity.Account;
import com.example.BMSprototype.entity.Member;

public interface AccountRepository extends JpaRepository<Account, Long> {
	boolean existsByAccountNumber(String accountNumber);
	
	Account findByMember(Member member);
	Account findByAccountNumber(String accountNumber);
	List<Account> findByMember_Id(Long id);
}
