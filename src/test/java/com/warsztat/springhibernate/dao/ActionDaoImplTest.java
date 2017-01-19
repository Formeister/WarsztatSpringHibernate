package com.warsztat.springhibernate.dao;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ActionDaoImplTest extends EntityDaoImplTest{
 
    @Autowired
    private ActionDao actionDao;
 
    @Override
    protected IDataSet getDataSet1() throws Exception{
        IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Action.xml"));
        return dataSet;
    }
    
    @Override
    protected IDataSet getDataSet2() throws Exception{
        IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User_Action.xml"));
        return dataSet;
    }
 
    @Test
    public void findById(){
        Assert.assertNotNull(actionDao.findById(1));
        Assert.assertNull(actionDao.findById(3));
    }
    
	@Test
	public void findByType(){
		Assert.assertNotNull(actionDao.findByType("NAPRAWA"));
		Assert.assertNull(actionDao.findByType("WYMIANA"));
	}
    
    @Test
    public void findAll(){
        Assert.assertEquals(actionDao.findAll().size(), 2);
    }
 
}
