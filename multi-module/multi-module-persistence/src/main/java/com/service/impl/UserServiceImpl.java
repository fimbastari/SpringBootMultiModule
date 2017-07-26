package com.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.domain.entity.User;
import com.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserDao userDao;
 
    public User findById(Long id) {
        return (User) userDao.findOne(id);
    }
 
    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }
 
    public void saveUser(User user) {
        userDao.save(user);
    }
 
    public void updateUser(User user){
        saveUser(user);
    }
 
    public void deleteUserById(Long id){
        userDao.delete(id);
    }
 
    public void deleteAllUsers(){
        userDao.deleteAll();
    }

    public List<User> findAllUsers(){
        return (List<User>) userDao.findAll();
    }

}
