package com.example.BMSprototype.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @ToString
@Table(name="bms_transaction")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "sender")
	private Member sender;
	
	@ManyToOne
	@JoinColumn(name = "receiver")
	private Member receiver;
	
	private int amount;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Transtype type; //거래 종류 DEPOSIT(입금)/TRANSFER(송금)
	
	@CreatedDate
	@Column(updatable=false)
	private LocalDateTime createdAt;
	
	@Builder
	public Transaction(Member sender,Member receiver,int amount,Transtype type) {
		this.sender=sender;
		this.receiver=receiver;
		this.amount=amount;
		this.type=type;
	}
}
