package com.example.BMSprototype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.BMSprototype.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtFilter;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.sessionManagement(session -> 
		session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.csrf(csrf->csrf.disable());
		http.formLogin(form -> form.disable());
		http.httpBasic(AbstractHttpConfigurer::disable);
		
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/**","/login").permitAll()
				.requestMatchers("/api/admin/*").hasRole("ADMIN")
				.requestMatchers("/api/banking/**").hasRole("USER")
				.anyRequest().authenticated()
				);
		
		
		/*
		http.formLogin(auth -> auth
				.loginPage("/login")
				.loginProcessingUrl("/loginProc")
				.defaultSuccessUrl("/success", true) 
				.failureUrl("/login?error") 
				);
		*/
		
		
		
		
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
}
