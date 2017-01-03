package com.warsztat.springhibernate.service;

import java.util.List;

import com.warsztat.springhibernate.model.User;
 
 
public interface UserService {
     
    User findById(int id);
     
    User findByPesel(String pesel);
     
    void saveUser(User user);
     
    void updateUser(User user);
     
    void deleteUserByPesel(String pesel);
 
    List<User> findAllUsers(); 
     
    boolean isUserPeselUnique(Integer id, String pesel);
 
}