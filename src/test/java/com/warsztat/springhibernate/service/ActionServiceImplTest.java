package com.warsztat.springhibernate.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
 
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

import com.warsztat.springhibernate.dao.ActionDao;
import com.warsztat.springhibernate.model.Action;
 
public class ActionServiceImplTest {
 
    @Mock
    ActionDao actionDao;
     
    @InjectMocks
    ActionServiceImpl actionService;
     
    @Spy
    List<Action> actions = new ArrayList<Action>();
     
    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        actions = getActionsList();
    }
 
    @Test
    public void findById(){
        Action act = actions.get(0);
        when(actionDao.findById(anyInt())).thenReturn(act);
        Assert.assertEquals(actionService.findById(act.getId()), act);
    }
    
    @Test
    public void findByType(){
        Action act = actions.get(0);
        Assert.assertEquals(actionService.findByType(anyString()), null);
        when(actionDao.findByType(anyString())).thenReturn(act);
        Assert.assertEquals(actionService.findByType(act.getType()), act);
    } 
    
	@Test
	public void findAll(){
		when(actionDao.findAll()).thenReturn(actions);
		Assert.assertEquals(actionService.findAll(), actions);
	}

	
    public List<Action> getActionsList(){
        Action a1 = new Action();
        a1.setId(1);
        a1.setType("NAPRAWA");
        
        Action a2 = new Action();
        a2.setId(2);
        a2.setType("SPRZEDAZ");

        actions.add(a1);
        actions.add(a2);
        return actions;
    }
     
}