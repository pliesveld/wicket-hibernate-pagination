package com.baeldung.common.web;

import com.baeldung.users.web.CreateRecord;
import com.baeldung.users.web.GenerateRecords;
import com.baeldung.users.web.ViewRecords;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class WebMiniApplication extends WebApplication
        implements ApplicationContextAware {
    private ApplicationContext ctx;

    @Override
    protected void init() {
        super.init();
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx, true));
        mountPage("/create", CreateRecord.class);
        mountPage("/view", ViewRecords.class);
        mountPage("/generate", GenerateRecords.class);
    }

    @Override
    public Class getHomePage() {
        return HomePage.class;
    }

    public WebMiniApplication() {}

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
