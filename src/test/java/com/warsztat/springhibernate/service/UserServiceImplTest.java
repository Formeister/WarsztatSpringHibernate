package com.warsztat.springhibernate.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
 
import java.util.ArrayList;
import java.util.List;
 
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 
import com.warsztat.springhibernate.dao.UserDao;
import com.warsztat.springhibernate.model.User;
 
public class UserServiceImplTest {
 
    @Mock
    UserDao userDao;
     
    @InjectMocks
    UserServiceImpl userService;
     
    @Spy
    List<User> users = new ArrayList<User>();
     
    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        users = getUsersList();
    }
 
    @Test
    public void findById(){
        User usr = users.get(0);
        when(userDao.findById(anyInt())).thenReturn(usr);
        Assert.assertEquals(userService.findById(usr.getId()), usr);
    }
    
    @Test
    public void findByPesel(){
        User usr = users.get(0);
        Assert.assertEquals(userService.findByPesel(anyString()), null);
        when(userDao.findByPesel(anyString())).thenReturn(usr);
        Assert.assertEquals(userService.findByPesel(usr.getPesel()), usr);
    } 
    
    @Test
    public void saveUser(){
        doNothing().when(userDao).save(any(User.class));
        userService.saveUser(any(User.class));
        verify(userDao, atLeastOnce()).save(any(User.class));
    }
    
    @Test
    public void updateUser(){
    	User usr = users.get(0);
        when(userDao.findById(anyInt())).thenReturn(usr);
        userService.updateUser(usr);
        verify(userDao, atLeastOnce()).findById(anyInt());
    }

    @Test
    public void deleteUserByPesel(){
        doNothing().when(userDao).deleteByPesel(anyString());
        userService.deleteUserByPesel(anyString());
        verify(userDao, atLeastOnce()).deleteByPesel(anyString());
    }
    
	@Test
	public void findAllUsers(){
		when(userDao.findAllUsers()).thenReturn(users);
		Assert.assertEquals(userService.findAllUsers(), users);
	}

    @Test
    public void isUserPeselUnique(){
        User usr = users.get(0);
        when(userDao.findByPesel(anyString())).thenReturn(usr);
        Assert.assertEquals(userService.isUserPeselUnique(usr.getId(), usr.getPesel()), true);
    }
	
    public List<User> getUsersList(){
        User u1 = new User();
        u1.setId(1);
        u1.setPesel("11432342342342");
        u1.setPassword("janpass");
        u1.setFirstName("Janek");
        u1.setLastName("Kowalski");
        u1.setEmail("janekk@mail.com");
        u1.setActions(null);
        
        User u2 = new User();
        u2.setId(2);
        u2.setPesel("534523423423423");
        u2.setPassword("zofiapass");
        u2.setFirstName("Zofia");
        u2.setLastName("Nowak");
        u2.setEmail("zofian@mail.com");
        u2.setActions(null);
         
        users.add(u1);
        users.add(u2);
        return users;
    }
     
}