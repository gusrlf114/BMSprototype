package com.example.BMSprototype.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="bms_mem")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Member {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false,unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role; //enum
}
