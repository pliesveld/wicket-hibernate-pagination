package com.baeldung;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.context.ApplicationContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

import com.baeldung.dao.UserDao;


import com.baeldung.common.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class } )
public class TestInject

{
    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationContext ctx;

    @Before
    public void initializeDB()
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.refresh();
    }

    @Test
    public void testFindCfg()
    {

        assertNotNull("userDao should be loadable",userDao);
        assertNotNull("ctx should be loadable",ctx);
        assertNotNull("cfg should be loadable",ctx.getBean("&sessionFactory"));
    }

}

