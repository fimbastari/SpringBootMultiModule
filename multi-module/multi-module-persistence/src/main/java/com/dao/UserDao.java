package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	List<User> findByName(String name);
}