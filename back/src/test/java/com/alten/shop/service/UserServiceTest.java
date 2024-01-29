package com.alten.shop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alten.shop.dto.SignupDTO;
import com.alten.shop.dto.UserDTO;
import com.alten.shop.entity.User;
import com.alten.shop.repository.UserRepository;
import com.alten.shop.service.exception.NotFoundException;
import com.github.javafaker.Faker;

/**
 * Test class for UserService
 * @Autor Dénez Fauchon
 * @see UserService
 */
@SpringBootTest
public class UserServiceTest {
	
	private static final Integer NB_USERS = 10;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserService userService;
	
	@Autowired
	private Faker faker;
	
	private List<User> usersWithId;
	
	private List<User> usersWithoutId;
	
	private List<UserDTO> usersDTOWithId;
	
	private List<SignupDTO> signupDTOs;
	
	@BeforeEach
	public void setUp() {
		
		usersWithId = new ArrayList<>();
		usersWithoutId = new ArrayList<>();
		usersDTOWithId = new ArrayList<>();
		signupDTOs = new ArrayList<>();
		String password;
		
		// create users, usersDTO and signupDTO
		for (int i = 0; i < NB_USERS; i++) {
			password = faker.internet().password();
			User userWithId = User.builder()
                    .id((long) i)
                    .username(faker.name().username())
                    .password(password)
                    .build();
			User userWithoutId = User.builder()
                    .username(userWithId.getUsername())
                    .password(userWithId.getPassword())
                    .build();
			UserDTO userDTOWithId = UserDTO.builder()
					.id(userWithId.getId())
                    .username(userWithId.getUsername())
                    .build();
			SignupDTO signupDTO = SignupDTO.builder()
					.username(userWithId.getUsername())
                    .password(password)
                    .build();
			
			usersWithId.add(userWithId);
			usersWithoutId.add(userWithoutId);
			usersDTOWithId.add(userDTOWithId);
			signupDTOs.add(signupDTO);
		}
	}
		
	/**
	 * Test of getAll method
     * Result: Success
	 */
	@DisplayName("Test getAll method - Success")
	@Test
	void testGetAll() {
        when(userRepository.findAll()).thenReturn(usersWithId);
        usersWithId.forEach(user -> 
        	when(modelMapper.map(user, UserDTO.class))
        	.thenReturn(usersDTOWithId.get(usersWithId.indexOf(user))));
        assertEquals(usersDTOWithId, userService.getAll());
    }
	
	/**
	 * Test of getAll method when no users are found
     * Result: Success
     */
	@DisplayName("Test getAll method when no users are found - Success")
	@Test
	void testGetAllWhenNoUsers() {
		when(userRepository.findAll()).thenReturn(new ArrayList<>());
		assertEquals(new ArrayList<>(), userService.getAll());
	}
	
	/**
     * Test of getById method
     * Result: Success
     */
	@DisplayName("Test getById method - Success")
	@Test
	void testGetById() throws Exception {
		User user;
		UserDTO userDTO;
		for (int i = 0; i < NB_USERS; i++) {
			user = usersWithId.get(i);
			userDTO = usersDTOWithId.get(i);
			
			when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
			when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
			
			assertEquals(userDTO, userService.getById(user.getId()));
		}
	}
	
	/**
	 * Test of getById method when user not found
     * Result: NotFoundException
     */
	@DisplayName("Test getById method when user not found - NotFoundException")
	@Test
	void testGetByIdWhenUserNotFound() {
		when(userRepository.findById(any())).thenReturn(java.util.Optional.empty());
		try {
			userService.getById(1L);
		} catch (NotFoundException e) {
			assertEquals("User with id 1 not found", e.getMessage());
		}
	}
	
	/**
     * Test of save method
     * Result: Success
     */
	@DisplayName("Test save method - Success")
	@Test
	void testSave() throws Exception {
		User user;
		UserDTO userDTO;
		SignupDTO signupDTO;
		for (int i = 0; i < NB_USERS; i++) {
			user = usersWithId.get(i);
			userDTO = usersDTOWithId.get(i);
			signupDTO = signupDTOs.get(i);
			
			when(passwordEncoder.encode(eq(signupDTO.getPassword()))).thenReturn(user.getPassword());
			when(userRepository.save(any())).thenReturn(user);
			when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

			assertEquals(userDTO, userService.save(signupDTO));
		}
	}
	
	/**
	 * Test of save method when user already exists Result:
	 * UserAlreadyExistsException
	 */
	@DisplayName("Test save method when user already exists - UserAlreadyExistsException")
	@Test
	void testSaveWhenUserAlreadyExists() {
		when(userRepository.existsByUsername(any())).thenReturn(true);
		try {
			userService.save(signupDTOs.get(0));
		} catch (Exception e) {
			assertEquals("User " + signupDTOs.get(0).getUsername() + " already exists", e.getMessage());
		}
	}
	
	/**
     * Test of update method
     * Result: Success
     */
	@DisplayName("Test update method - Success")
	@Test
	void testUpdate() throws Exception {
		User user;
		UserDTO userDTO;
		for (int i = 0; i < NB_USERS; i++) {
			user = usersWithId.get(i);
			userDTO = usersDTOWithId.get(i);

			when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
			when(modelMapper.map(userDTO, User.class)).thenReturn(user);
			when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

			assertEquals(userDTO, userService.update(user.getId(), userDTO));
		}
	}
	
	/**
     * Test of update method when user not found
     * Result: NotFoundException
     */
	@DisplayName("Test update method when user not found - NotFoundException")
	@Test
	void testUpdateWhenUserNotFound() {
		when(userRepository.findById(any())).thenReturn(java.util.Optional.empty());
		try {
			userService.update(1L, usersDTOWithId.get(0));
		} catch (NotFoundException e) {
			assertEquals("User with id 1 not found", e.getMessage());
		}
	}
	
	/**
     * Test of delete method
     * Result: Success
     */
	@DisplayName("Test delete method - Success")
	@Test
	void testDelete() throws Exception {
		User user;
		for (int i = 0; i < NB_USERS; i++) {
			user = usersWithId.get(i);

			when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

			userService.delete(user.getId());
		}
	}
	
	/**
     * Test of delete method when user not found
     * Result: NotFoundException
     */
	@DisplayName("Test delete method when user not found - NotFoundException")
	@Test
	void testDeleteWhenUserNotFound() {
		when(userRepository.findById(any())).thenReturn(java.util.Optional.empty());
		try {
			userService.delete(1L);
		} catch (NotFoundException e) {
			assertEquals("User with id 1 not found", e.getMessage());
		}
	}
}
