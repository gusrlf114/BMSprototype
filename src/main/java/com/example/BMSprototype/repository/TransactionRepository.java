package com.example.BMSprototype.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BMSprototype.entity.Member;
import com.example.BMSprototype.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findTop5BySenderOrReceiverOrderByCreatedAtDesc(Member sender,Member receiver);
}
