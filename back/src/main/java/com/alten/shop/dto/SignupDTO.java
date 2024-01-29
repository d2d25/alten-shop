package com.alten.shop.dto;

import java.util.List;

import com.alten.shop.entity.Role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupDTO {
	
	@NotNull(message = "Username cannot be null")
	@NotEmpty(message = "Username cannot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must be alphanumeric")
	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	private String username;
	
	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be empty")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	private String password;
	
	private List<Role> roles;
	
	public SignupDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
