package com.example.BMSprototype.dto;

import java.util.List;

import com.example.BMSprototype.entity.Transaction;

import lombok.Data;
@Data
public class ReadAccountDto {

	private List<Transaction> list;
	
	private int myBalance;
}
