package com.alten.shop.service;

import java.text.MessageFormat;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alten.shop.dto.SignupDTO;
import com.alten.shop.dto.UserDTO;
import com.alten.shop.entity.Role;
import com.alten.shop.entity.User;
import com.alten.shop.repository.UserRepository;
import com.alten.shop.service.exception.NotFoundException;
import com.alten.shop.service.exception.UserAlreadyExistsException;

@Service
public class UserService implements IUserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public List<UserDTO> getAll() {
		return userRepository.findAll().stream().map(
				user -> modelMapper.map(user, UserDTO.class))
				.toList();
	}

	@Override
	public UserDTO getById(Long id) throws NotFoundException {
		return modelMapper.map(getUserById(id), UserDTO.class);
	}

	@Override
	public UserDTO save(SignupDTO signupDTO) throws UserAlreadyExistsException {
		signupDTO.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
		logger.info("Attempting registration for user: {}", signupDTO.getUsername());
		if (userRepository.existsByUsername(signupDTO.getUsername())) {
			throw new UserAlreadyExistsException(
					MessageFormat.format("User {0} already exists", signupDTO.getUsername()));
		}
		User user = null;
		if (signupDTO.getRoles() == null || signupDTO.getRoles().isEmpty()) {
			logger.info("No roles provided, defaulting to USER");
			user = userRepository.save(new User(signupDTO.getUsername(), signupDTO.getPassword(), List.of(Role.USER)));
		}
		else {
			logger.info("Roles provided, saving user with provided roles");
            user = userRepository.save(new User(signupDTO.getUsername(), signupDTO.getPassword(), signupDTO.getRoles()));
        }
		logger.info("User registered successfully");
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public UserDTO update(Long id, UserDTO userDTO) throws NotFoundException {
		User user = getUserById(id);
		// set id to user to Null to avoid replacing the id user
		userDTO.setId(null);
		User newUser = modelMapper.map(userDTO, User.class);

		// update user with new user
		modelMapper.map(newUser, user);

		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public void delete(Long id) throws NotFoundException {
		User user = getUserById(id);
		userRepository.delete(user);
	}
	
	private User getUserById(Long id) throws NotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("User with id {0} not found", id)));
	}

}
