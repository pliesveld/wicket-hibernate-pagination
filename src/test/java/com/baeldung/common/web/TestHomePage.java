package com.baeldung.common.web;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.common.SpringConfig;

// TODO: add testing models https://ci.apache.org/projects/wicket/guide/6.x/guide/testing.html#testing_1

/**
 * Uses the same Spring ApplicationContext as deployment
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class } )
public class TestHomePage
{
    private WicketTester tester;

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private WebMiniApplication webMiniApplication;

    @Before
    public void setUp()
    {
        tester = new WicketTester(webMiniApplication);
    }

    @Test
    public void homepageRendersSuccessfully()
    {
        tester.startPage(HomePage.class);
        tester.assertRenderedPage(HomePage.class);
    }

}
