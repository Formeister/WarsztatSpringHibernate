package com.warsztat.springhibernate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.warsztat.springhibernate.dao.ActionDao;
import com.warsztat.springhibernate.model.Action;
 
 
@Service("actionService")
@Transactional
public class ActionServiceImpl implements ActionService{
     
    @Autowired
    ActionDao dao;
     
    public Action findById(int id) {
        return dao.findById(id);
    }
 
    public Action findByType(String type){
        return dao.findByType(type);
    }
 
    public List<Action> findAll() {
        return dao.findAll();
    }
}