package com.baeldung;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.BeansException;

import org.hibernate.cfg.Configuration;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

import com.baeldung.dao.UserDao;


import com.baeldung.common.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class } )
public class TestSchema
    implements ApplicationContextAware

{
    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationContext ctx;
    private Configuration cfg;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.ctx = applicationContext;
    }


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
        Object cfg_obj = ctx.getBean("&sessionFactory");
        assertNotNull("session factory not initialized",cfg_obj);


        if(cfg_obj instanceof org.springframework.orm.hibernate5.LocalSessionFactoryBean)
        {
            org.springframework.orm.hibernate5.LocalSessionFactoryBean s_factory =
                (org.springframework.orm.hibernate5.LocalSessionFactoryBean) cfg_obj;

            cfg = s_factory.getConfiguration();
            assertNotNull("cfg should be loadable",cfg);

            // http://stackoverflow.com/questions/32178041/where-did-configuration-generateschemacreationscript-go-in-hibernate-5
            // SchemaExporter
        }



    }

}

