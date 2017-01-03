package com.warsztat.springhibernate.dao;

import java.util.List;

import com.warsztat.springhibernate.model.User;
 
 
public interface UserDao {
 
    User findById(int id);
     
    User findByPesel(String pesel);
     
    void save(User user);

    void deleteByPesel(String pesel);
     
    List<User> findAllUsers();
     
    List<User> searchByAction(Integer actionId); 
    
    List<User> searchByLastName(String lastName); 
 
}