package com.alten.shop.service;

import java.text.MessageFormat;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.alten.shop.repository.UserRepository;
import com.alten.shop.service.exception.UserAlreadyExistsException;
import com.alten.shop.dto.SignupDTO;
import com.alten.shop.entity.Role;
import com.alten.shop.entity.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.info("Attempting login for user: {}", username);
		
		return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User {0} not found", username)));
		
	}
}
