package com.warsztat.springhibernate.dao;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.BeforeClass;

import com.warsztat.springhibernate.configuration.JpaConfigurationTest;
 
//@SuppressWarnings("deprecation")
@ContextConfiguration(classes = { JpaConfigurationTest.class })
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
public abstract class EntityDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {
 
    @Autowired
    DataSource dataSource;
 
    @BeforeClass
    public void setUp() throws Exception {
        IDatabaseConnection dbConn = new DatabaseDataSourceConnection(
                dataSource);
        DatabaseOperation.DELETE.execute(dbConn, getDataSet2());
        DatabaseOperation.CLEAN_INSERT.execute(dbConn, getDataSet1());
    }
     
    protected abstract IDataSet getDataSet1() throws Exception;
    protected abstract IDataSet getDataSet2() throws Exception;
 
}
