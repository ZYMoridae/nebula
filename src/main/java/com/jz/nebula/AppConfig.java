package com.jz.nebula;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AppConfig {

	//I've got my data source defined in application.yml config file, 
	//so there is no need to configure it from java.
	@Autowired
	DataSource dataSource;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	    //JpaVendorAdapteradapter can be autowired as well if it's configured in application properties.
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(false);

	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    //Add package to scan for entities.
	    factory.setPackagesToScan("com.jz.nebula");
	    factory.setDataSource(dataSource);
	    return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory);
	    return txManager;
	}
    
}
