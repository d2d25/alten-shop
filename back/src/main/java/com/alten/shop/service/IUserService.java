package com.alten.shop.service;

import java.util.List;

import com.alten.shop.dto.SignupDTO;
import com.alten.shop.dto.UserDTO;
import com.alten.shop.service.exception.NotFoundException;
import com.alten.shop.service.exception.UserAlreadyExistsException;

public interface IUserService {
	
	/*
	 * Method to get all users
	 * @return List<UserDTO> : list of users found
     */
    public List<UserDTO> getAll();
    
    /*
     * Method to get user by id
     * @param id : user id to get
     * @return UserDTO : user found
     * @throws NotFoundException if user not found
     */
    public UserDTO getById(Long id) throws NotFoundException;
    
    /*
     * Method to save user
     * @param signupDto : user to save
     * @return UserDTO : saved user
     */
    public UserDTO save(SignupDTO signupDto) throws UserAlreadyExistsException;
    
    /*
     * Method to update user
     * @param id : user id to update
     * @param userDTO : UserDTO to update
     * @return UserDTO : updated user
     * @throws NotFoundException if user not found
     */
    public UserDTO update(Long id, UserDTO userDTO) throws NotFoundException;
    
    /*
     * Method to delete user
     * @param id : user id to delete
     */
    public void delete(Long id) throws NotFoundException;
}
