package com.example.BMSprototype.dto;

import com.example.BMSprototype.entity.Role;

import lombok.Data;

@Data
public class MemberDto {
	
	private String username;
	
	private String password;
	
	private Role role;
}
