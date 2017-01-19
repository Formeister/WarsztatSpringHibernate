package com.warsztat.springhibernate.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
 
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.Mockito.atLeastOnce;

import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 
 
import com.warsztat.springhibernate.model.User;
import com.warsztat.springhibernate.model.Action;
import com.warsztat.springhibernate.service.ActionService;
import com.warsztat.springhibernate.service.UserService;
 

public class AppControllerTest {
 
    @Mock
    UserService userService;
     
    @Mock
    ActionService actionService;
     
    @Mock
    MessageSource messageSource;
     
    @InjectMocks
    AppController appController;
     
    @Spy
    List<User> users = new ArrayList<User>();
    
    @Spy
    List<Action> actions = new ArrayList<Action>();
 
    @Spy
    ModelMap model;
     
    @Mock
    BindingResult result;
     
    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        users = getUsersList();
        //actions = getActionsList();
    }
     
    @Test
    public void listUsers(){
        when(userService.findAllUsers()).thenReturn(users);
        Assert.assertEquals(appController.listUsers(model), "userslist");
        Assert.assertEquals(model.get("users"), users);
        verify(userService, atLeastOnce()).findAllUsers();
    }
     
    @Test
    public void newUser(){
        Assert.assertEquals(appController.newUser(model), "registration");
        Assert.assertNotNull(model.get("user"));
        Assert.assertFalse((Boolean)model.get("edit"));
        Assert.assertEquals(((User)model.get("user")).getId(), null);
    }
 
 
    @Test
    public void saveUserWithValidationError(){
        when(result.hasErrors()).thenReturn(true);
        doNothing().when(userService).saveUser(any(User.class));
        Assert.assertEquals(appController.saveUser(users.get(0), result, model), "registration");
    }
 
    @Test
    public void saveUserWithValidationErrorNonUniquePesel(){
        when(result.hasErrors()).thenReturn(false);
        when(userService.isUserPeselUnique(anyInt(), anyString())).thenReturn(false);
        Assert.assertEquals(appController.saveUser(users.get(0), result, model), "registration");
    }
 
     
    @Test
    public void saveUserWithSuccess(){
        when(result.hasErrors()).thenReturn(false);
        when(userService.isUserPeselUnique(anyInt(), anyString())).thenReturn(true);
        doNothing().when(userService).saveUser(any(User.class));
        Assert.assertEquals(appController.saveUser(users.get(0), result, model), "registrationsuccess");
        Assert.assertEquals(model.get("success"), "User Axel Crook registered successfully");
    }
 
    @Test
    public void editUser(){
        User usr = users.get(0);
        when(userService.findByPesel(anyString())).thenReturn(usr);
        Assert.assertEquals(appController.editUser(anyString(), model), "registration");
        Assert.assertNotNull(model.get("user"));
        Assert.assertTrue((Boolean)model.get("edit"));
        Assert.assertEquals(((User)model.get("user")).getId(), (Integer)1);
    }
 
    @Test
    public void updateUserWithValidationError(){
        when(result.hasErrors()).thenReturn(true);
        doNothing().when(userService).updateUser(any(User.class));
        Assert.assertEquals(appController.updateUser(users.get(0), result, model,""), "registration");
    }
 
    @Test
    public void updateUserWithValidationErrorNonUniquePesel(){
        when(result.hasErrors()).thenReturn(false);
        when(userService.isUserPeselUnique(anyInt(), anyString())).thenReturn(false);
        Assert.assertEquals(appController.updateUser(users.get(0), result, model,""), "registration");
    }
 
    @Test
    public void updateUserWithSuccess(){
        when(result.hasErrors()).thenReturn(false);
        when(userService.isUserPeselUnique(anyInt(), anyString())).thenReturn(true);
        doNothing().when(userService).updateUser(any(User.class));
        Assert.assertEquals(appController.updateUser(users.get(0), result, model, ""), "registrationsuccess");
        Assert.assertEquals(model.get("success"), "User Axel Crook updated successfully");
    }
     
     
    @Test
    public void deleteUser(){
        doNothing().when(userService).deleteUserByPesel(anyString());
        Assert.assertEquals(appController.deleteUser("123"), "redirect:/list");
    }
     
    @Test
    public void initializeActions(){
        Assert.assertEquals(appController.initializeActions(), actionService.findAll());
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