package com.fintec.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySources({
	@PropertySource("classpath:db.properties"),
	@PropertySource("classpath:securityconfig.properties"),
	@PropertySource("classpath:tomcatconfig.properties")
})

@EnableJpaRepositories(basePackages = { "com.fintec.repositories" })
public class JPAConfiguration {

	@Autowired
	private Environment environment;
	
	@Autowired
	TextEncryptor textEntryptor;
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(getDataSource());
		factory.setPackagesToScan(new String[] { "com.fintec.oauth.model", "com.fintec.model" });
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		factory.setJpaProperties(getJPAProperties());
		factory.afterPropertiesSet();
		return factory;
	}
	

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabasePlatform(environment.getProperty("hibernate.dialect"));
		return hibernateJpaVendorAdapter;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(environment.getProperty("db.driverClass"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl(environment.getProperty("db.Url"));
		cpds.setUser(textEntryptor.decrypt(environment.getProperty("db.UserName")));
		cpds.setPassword(textEntryptor.decrypt(environment.getProperty("db.Password")));
		cpds.setMinPoolSize(Integer.parseInt(environment.getProperty("c3p0.min_size")));
		cpds.setMaxPoolSize(Integer.parseInt(environment.getProperty("c3p0.max_size")));
		return cpds;
	}
	
	private Properties getJPAProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		//properties.put("hibernate.default_schema", environment.getProperty("hibernate.default_schema"));
		//properties.put("hibernate.default_catalog", environment.getProperty("hibernate.default_catalog"));
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		//properties.put("hibernate.jdbc.lob.non_contextual_creation", environment.getProperty("hibernate.jdbc.lob.non_contextual_creation"));
		properties.put("hibernate.current_session_context_class", "thread");
		properties.put("org.hibernate.flushMode", "ALWAYS");
		properties.put("hibernate.flushMode", "ALWAYS");
		return properties;
	}

	@Bean
	@Autowired
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

	@Bean
	@Autowired
	public SessionFactory sessionFactory(EntityManagerFactory emf) {
		try {
			return emf.unwrap(SessionFactory.class);
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	@Bean 
	public TextEncryptor textEncryptor(){
		return TextEncryptorConfig.valueOf(environment.getProperty("text.encryption.level")).
				getTextEncryptor(environment.getProperty("text.encryption.password"), environment.getProperty("text.encryption.key"));
	}
	
	@Bean 
	public BytesEncryptor bytesEncryptor(){
		return ByteEncryptorConfig.valueOf(environment.getProperty("byte.encryption.level")).
				getBytesEncryptor(environment.getProperty("byte.encryption.password"), environment.getProperty("byte.encryption.key"));
	}
}