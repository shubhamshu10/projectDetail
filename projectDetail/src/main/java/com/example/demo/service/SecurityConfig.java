package com.example.demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();	
    }
	@Bean
	public UserDetailsService getService() {
		return new EmployeeServiceImpl();
	}
	public DaoAuthenticationProvider getProvider() {
		DaoAuthenticationProvider dao= new DaoAuthenticationProvider();
		dao.setUserDetailsService(getService());
		dao.setPasswordEncoder(getPasswordEncoder());
		return dao;
		}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasRole("USER").antMatchers("/**").permitAll().and()
		.formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password")
		.loginProcessingUrl("/login").defaultSuccessUrl("/employee/add project").and().csrf().disable();
	}

}
