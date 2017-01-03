package com.warsztat.springhibernate.dao;

import java.util.List;

import javax.persistence.NoResultException;
 
import org.springframework.stereotype.Repository;
 
import com.warsztat.springhibernate.model.Action;
 

@Repository("actionDao")
public class ActionDaoImpl extends AbstractDao<Integer, Action>implements ActionDao{
 
    public Action findById(int id) {
        return getByKey(id);
    }
 
    public Action findByType(String type) {
        System.out.println("type: "+type);
        try{
        	Action action = (Action) getEntityManager()
                    .createQuery("SELECT p FROM Action p WHERE p.type LIKE :type")
                    .setParameter("type", type)
                    .getSingleResult();
            return action; 
        }catch(NoResultException ex){
            return null;
        }
    }
     
    @SuppressWarnings("unchecked")
    public List<Action> findAll(){
        List<Action> actions = getEntityManager()
                .createQuery("SELECT p FROM Action p  ORDER BY p.type ASC")
                .getResultList();
        return actions;
    }
     
}