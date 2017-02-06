package com.warsztat.springhibernate.dao;

import java.util.Collection;
import java.util.List;
 
import javax.persistence.NoResultException;
 
import org.springframework.stereotype.Repository;

import com.warsztat.springhibernate.model.User;
 
 
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {
 
    public User findById(int id) {
        User user = getByKey(id);
        if(user!=null){
            initializeCollection(user.getActions());
        }
        return user;
    }
 
    public User findByPesel(String pesel ) {
        System.out.println("PESEL : "+pesel);
        try{
            User user = (User) getEntityManager()
                    .createQuery("SELECT u FROM User u WHERE u.pesel LIKE :pesel")
                    .setParameter("pesel", pesel)
                    .getSingleResult();
             
            if (user != null){
                initializeCollection(user.getActions());
            }
            return user; 
        }catch(NoResultException ex){
            return null;
        }
    }
     
    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        List<User> users = getEntityManager()
                .createQuery("SELECT u FROM User u ORDER BY u.firstName ASC")
                .getResultList();
        return users;
    }
    
    // COUNT USERS
    public int countAllUsers(){
    	int count = 0;
    	count = findAllUsers().size();
    	return count;
    }
 
    public void save(User user) {
        persist(user);
    }
 
    public void deleteByPesel(String pesel) {
    	User user = null;
    	try{
	        user = (User) getEntityManager()
	                .createQuery("SELECT u FROM User u WHERE u.pesel LIKE :pesel")
	                .setParameter("pesel", pesel)
	                .getSingleResult();
    	}
    	catch (NoResultException e){		
    	}
        if (user != null) delete(user);      
    }
    
    @SuppressWarnings("unchecked")
	public List<User> searchByAction(Integer actionId) {
    	List<User> users = getEntityManager()		
    			.createQuery("SELECT u FROM User u " +
    				    	 "JOIN u.actions a " +
    				    	 "WHERE a.id = :action_id")
                .setParameter("action_id", actionId)
                .getResultList();
        return users;
    }
    
    @SuppressWarnings("unchecked")
	public List<User> searchByLastName(String lastName) {
    	List<User> users = getEntityManager()		
    			.createQuery("SELECT u FROM User u WHERE u.lastName LIKE :last_name ORDER BY u.lastName ASC")
                .setParameter("last_name", lastName)
                .getResultList();
        return users;
    }
    
    //An alternative to Hibernate.initialize()
    protected void initializeCollection(Collection<?> collection) {
        if(collection == null) {
            return;
        }
        collection.iterator().hasNext();
    }
 
}