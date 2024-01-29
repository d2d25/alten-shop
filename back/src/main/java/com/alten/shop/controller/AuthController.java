package com.alten.shop.controller;

import java.net.URI;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.shop.config.utils.JwtService;
import com.alten.shop.dto.JwtResponseDTO;
import com.alten.shop.dto.LoginDTO;
import com.alten.shop.dto.SignupDTO;
import com.alten.shop.dto.UserDTO;
import com.alten.shop.service.IUserService;
import com.alten.shop.service.UserDetailsServiceImpl;
import com.alten.shop.service.exception.UserAlreadyExistsException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private IUserService userService;

	/**
	 * Method to authenticate a user.
	 * @param LoginDTO : user to authenticate
	 * @return Response : response with token
	 */
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        logger.info("Attempting login for user: {}", loginDTO.getUsername());
		Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
		
		if (!violations.isEmpty()) {
			return ResponseEntity.badRequest().body(violations);
		}
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		
		if (authentication.isAuthenticated()) {
			return ResponseEntity.ok(
					JwtResponseDTO.builder()
					.accessToken(
							jwtService.generateToken(loginDTO.getUsername())
					        ).build());
		}
		logger.info("Invalid username or password for user: {}", loginDTO.getUsername());
		return ResponseEntity.status(401).body("Invalid username or password");
	}
	
	/**
     * Method to register a user.
     * @param SignupDTO : user to register
     * @return Response : response with token
     */
	@PostMapping("/register")
	public ResponseEntity signup(@RequestBody SignupDTO signupDTO) {
        logger.info("Attempting registration for user: {}", signupDTO.getUsername());
		Set<ConstraintViolation<SignupDTO>> violations = validator.validate(signupDTO);

		if (!violations.isEmpty()) {
			return ResponseEntity.badRequest().body(violations);
		}
		try {
			UserDTO userDto = userService.save(signupDTO);
			logger.info("User {} registered successfully", signupDTO.getUsername());

			return ResponseEntity.created(URI.create("/users/" + userDto.getId())).body(userDto);
		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
}
