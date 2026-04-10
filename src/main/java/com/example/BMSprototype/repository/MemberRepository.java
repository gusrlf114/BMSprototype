package com.example.BMSprototype.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BMSprototype.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByUsername(String username);
	
	
}
