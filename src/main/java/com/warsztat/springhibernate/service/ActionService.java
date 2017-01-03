package com.warsztat.springhibernate.service;

import java.util.List;

import com.warsztat.springhibernate.model.Action;
 
 
public interface ActionService {
 
	Action findById(int id);
 
	Action findByType(String type);
     
    List<Action> findAll();
     
}