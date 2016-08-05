package com.baeldung.service;

import com.baeldung.common.SpringConfig;
import com.baeldung.model.UserDetails;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.EnumSet;

/**
 * Generates database schemas for annotated classes.  Requires dialects and naming schemes.
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

        for (String profile : profiles)
            sb.append(profile);

        System.out.println("Exporting Schema for profile: " + sb.toString());

        Class<?> annotated_class = UserDetails.class;
        String annotated_class_name = annotated_class.getSimpleName();

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", properties.getRequiredProperty("jdbc.driverClassName"))
                .applySetting("hibernate.connection.url", properties.getRequiredProperty("jdbc.url"))
                .applySetting("hibernate.connection.username", properties.getRequiredProperty("jdbc.username"))
                .applySetting("hibernate.connection.password", properties.getRequiredProperty("jdbc.password"))
                .applySetting("hibernate.dialect", properties.getRequiredProperty("hibernate.dialect"))
                .build();

        MetadataSources metadata = new MetadataSources(registry)
                .addAnnotatedClass(UserDetails.class);

        String filename_export = String.format("%s.ddl", annotated_class_name);

        SchemaExport export = new SchemaExport();

        export.setOutputFile(filename_export);
        export.setDelimiter(";");
        export.setFormat(true);
        export.setHaltOnError(true);

        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.STDOUT, TargetType.SCRIPT);

        export.createOnly(targetTypes, metadata.buildMetadata());

        System.out.println("Exported:" + filename_export);
    }

}
