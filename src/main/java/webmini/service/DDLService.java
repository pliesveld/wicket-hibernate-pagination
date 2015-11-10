package webmini.service;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import static org.hibernate.tool.hbm2ddl.Target.NONE;
import static org.hibernate.tool.hbm2ddl.Target.BOTH;
import static org.hibernate.tool.hbm2ddl.Target.SCRIPT;
import static org.hibernate.tool.hbm2ddl.Target.EXPORT;

import webmini.model.UserDetails;

/**
 * Generates database schemas for annotated classes.  Requires dialects and naming schemes. 
 * 
 *
 */
public class DDLService {
	public void DDLService() {}
	
	public static void main(String[] args) {
		Class<?> annotated_class = UserDetails.class;
		String annotated_class_name = annotated_class.getSimpleName();
		
		MetadataSources metadata = new MetadataSources(
				new StandardServiceRegistryBuilder()
				.applySetting("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
				.build());

		metadata.addAnnotatedClass(annotated_class);

		SchemaExport export = new SchemaExport(
				(MetadataImplementor) metadata.buildMetadata());
		
		export.setDelimiter(";");
		export.setFormat(true);
		
	    export.setOutputFile(String.format("%s.ddl",annotated_class_name));
	    export.drop(SCRIPT);
		export.create(SCRIPT);
	}

}
