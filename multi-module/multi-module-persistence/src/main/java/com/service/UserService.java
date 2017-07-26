package com.service;

import java.util.List;

import com.domain.entity.User;

public interface UserService {

	public User findById(Long id);

	public List<User> findByName(String name);

	public void saveUser(User user);

	public void updateUser(User user);

	public void deleteUserById(Long id);

	public void deleteAllUsers();

	public List<User> findAllUsers();

}
