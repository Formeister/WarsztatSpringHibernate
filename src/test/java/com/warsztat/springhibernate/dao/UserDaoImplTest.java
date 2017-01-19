package com.warsztat.springhibernate.dao;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
 
import com.warsztat.springhibernate.model.User;
import com.warsztat.springhibernate.dao.UserDao; 
 

public class UserDaoImplTest extends EntityDaoImplTest{
 
    @Autowired
    private UserDao userDao;
 
    @Override
    protected IDataSet getDataSet1() throws Exception {
      IDataSet[] datasets = new IDataSet[] {
              new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User.xml")),
              new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Action.xml")),
              new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User_Action.xml"))
      };
      return new CompositeDataSet(datasets);
    }
    
    @Override
    protected IDataSet getDataSet2() throws Exception{
        IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User_Action.xml"));
        return dataSet;
    }
 
    @Test
    public void findById(){
        Assert.assertNotNull(userDao.findById(1));
        Assert.assertNull(userDao.findById(3));
    }
    
	@Test
	public void findByPesel(){
		Assert.assertNotNull(userDao.findByPesel("4234241231"));
		Assert.assertNull(userDao.findByPesel("14545"));
	}
    
    @Test
    public void findAllUsers(){
        Assert.assertEquals(userDao.findAllUsers().size(), 2);
    }
     
    @Test
    public void save(){
    	userDao.save(getSampleUser());
        Assert.assertEquals(userDao.findAllUsers().size(), 3);
    }
     
    @Test
    public void deleteByPesel(){
    	Assert.assertEquals(userDao.findAllUsers().size(), 2);
    	userDao.deleteByPesel("4234241231");
        Assert.assertEquals(userDao.findAllUsers().size(), 1);
        Assert.assertNull(userDao.findByPesel("4234241231"));
    }
     
    @Test
    public void deleteUserByInvalidPesel(){
    	Assert.assertEquals(userDao.findAllUsers().size(), 2);
    	userDao.deleteByPesel("2342334421");
        Assert.assertEquals(userDao.findAllUsers().size(), 2);
    }
    
    @Test
    public void searchByAction(){
    	Assert.assertEquals(userDao.searchByAction(2).size(), 2);
    	Assert.assertEquals(userDao.searchByAction(2).get(0), userDao.findById(1));
    }
    
    @Test
    public void searchByLastName(){
    	Assert.assertEquals(userDao.searchByLastName("Kowalski").size(), 1);
    	Assert.assertEquals(userDao.searchByLastName("Grzanka").size(), 0);
    	Assert.assertEquals(userDao.searchByLastName("Kowalski").get(0), userDao.findById(1));
    }
  
 
    public User getSampleUser(){
    	User user = new User();
    	user.setPesel("234236745645");
    	user.setPassword("karolpass");
    	user.setFirstName("Karol");
    	user.setLastName("Tramp");
    	user.setEmail("ktramp@mail.com");
    	user.setActions(null);
        return user;
    }
 
}
