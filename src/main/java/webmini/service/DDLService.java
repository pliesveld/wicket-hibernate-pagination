package webmini.service;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import static org.hibernate.tool.hbm2ddl.Target.NONE;
import static org.hibernate.tool.hbm2ddl.Target.BOTH;
import static org.hibernate.tool.hbm2ddl.Target.SCRIPT;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.hibernate.tool.hbm2ddl.Target.EXPORT;

import webmini.common.SpringConfig;
import webmini.model.UserDetails;

/**
 * Generates database schemas for annotated classes.  Requires dialects and naming schemes.
 *
 *
 */

public class DDLService {
	
    public void DDLService() {}

    public static void main(String[] args) {

	    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
	
	    Environment properties = ctx.getBean(Environment.class);
	    
	    System.out.println("ApplicationContext: " + ctx.getDisplayName());
	    
	    StringBuilder sb = new StringBuilder();
	    
	    String[] profiles = (ctx.getEnvironment().getActiveProfiles().length > 0 ?
	    		ctx.getEnvironment().getActiveProfiles() 
	    		: ctx.getEnvironment().getDefaultProfiles());
	    
	    for(String profile : profiles)
	    	sb.append(profile);
	    
	    System.out.println("Exporting Schema for profile: " + sb.toString());
	
	    Class<?> annotated_class = UserDetails.class;
	    String annotated_class_name = annotated_class.getSimpleName();
	
	    StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
	    		.applySetting("hibernate.connection.driver_class",properties.getRequiredProperty("jdbc.driverClassName"))
	    		.applySetting("hibernate.connection.url",properties.getRequiredProperty("jdbc.url"))
	    		.applySetting("hibernate.connection.username",properties.getRequiredProperty("jdbc.username"))
	    		.applySetting("hibernate.connection.password",properties.getRequiredProperty("jdbc.password"))
	    		.applySetting("hibernate.dialect",properties.getRequiredProperty("hibernate.dialect"))
	    	    .build();
	
	    Metadata metadata = new MetadataSources(registry)
	    		.addAnnotatedClass(UserDetails.class)
	    		.buildMetadata();
	   
	    String filename_export = String.format("%s.ddl",annotated_class_name); 
	    
	    new SchemaExport((MetadataImplementor) metadata)
	        .setOutputFile(filename_export)
	        .setDelimiter(";")
	        .setFormat(true)
	        .setHaltOnError(true)
	    	.create(NONE);
	    
	    System.out.println("Exported:" + filename_export);
    }

}
