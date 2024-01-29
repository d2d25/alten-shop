package com.alten.shop.dto;

import java.util.List;

import com.alten.shop.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	
	private Long id;
	private String username;
	private List<Role> roles;
	
}
