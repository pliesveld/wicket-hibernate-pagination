package webmini.common.web;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import webmini.common.web.HomePage;

import webmini.users.web.*;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContextAware;

@Component
public class WebMiniApplication extends WebApplication
    implements ApplicationContextAware
{
    final static Logger LOG = LogManager.getLogger(WebMiniApplication.class);
    private ApplicationContext ctx;

    @Override
    protected void init() {
        super.init();
        LOG.debug("WebMiniApplication::init");
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);
        getComponentInstantiationListeners().add(new SpringComponentInjector(this,ctx,true));

        this.mountPage("/create", CreateRecord.class);
        this.mountPage("/view", ViewRecords.class);
        this.mountPage("/generate", GenerateRecords.class);
    }

    @Override
    public Class getHomePage()
    {
        return HomePage.class;
    }

    public WebMiniApplication() {}

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.ctx = applicationContext;
    }
}
