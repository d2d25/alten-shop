package com.alten.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
	
	@NotNull(message = "Username cannot be null")
	@NotEmpty(message = "Username cannot be empty")
	private String username;
	
	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be empty")
	private String password;
}
