package com.warsztat.springhibernate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.warsztat.springhibernate.dao.UserDao;
import com.warsztat.springhibernate.model.User;
 
 
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
 
    @Autowired
    private UserDao dao;
 
    public User findById(int id) {
        return dao.findById(id);
    }
 
    public User findByPesel(String pesel) {
        User user = dao.findByPesel(pesel);
        return user;
    }
 
    public void saveUser(User user) {
        dao.save(user);
    }
    
    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateUser(User user) {
        User entity = findById(user.getId());
        if (entity != null) {
            entity.setPesel(user.getPesel());
            entity.setPassword(user.getPassword());
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            entity.setActions(user.getActions());
        }
    }
 
    public void deleteUserByPesel(String pesel) {
        dao.deleteByPesel(pesel);
    }
 
    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }
 
    public boolean isUserPeselUnique(Integer id, String pesel) {
        User user = findByPesel(pesel);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }
     
}