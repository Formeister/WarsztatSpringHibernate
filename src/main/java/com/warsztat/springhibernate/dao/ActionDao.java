package com.warsztat.springhibernate.dao;

import java.util.List;

import com.warsztat.springhibernate.model.Action;
 
 
public interface ActionDao {
 
    List<Action> findAll();
     
    Action findByType(String type);
     
    Action findById(int id);
}