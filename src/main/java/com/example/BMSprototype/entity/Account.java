package com.example.BMSprototype.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="bms_account")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)
	private String accountNumber;
	
	private int balance;
	
	@ManyToOne
	@JoinColumn(name = "memid")
	private Member member;
	
	private boolean isActive =true;
}
