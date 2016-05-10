package com.gcit.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.lms.dao.AuthorDAO;
import com.lms.dao.BookCopiesDAO;
import com.lms.dao.BookDAO;
import com.lms.dao.BookLoansDAO;
import com.lms.dao.BorrowerDAO;
import com.lms.dao.GenreDAO;
import com.lms.dao.LibraryBranchDAO;
import com.lms.dao.PublisherDAO;
import com.lms.service.AdministratorService;
import com.lms.service.BorrowerServices;
import com.lms.service.LibrarianService;
import com.mongodb.MongoClient;

@EnableTransactionManagement
@Configuration
public class LMSConfig {
	
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/library";
	private static final String user = "root";
	private static final String pwd = "5883";
	
	@Bean
	public AdministratorService adminService(){
		return new AdministratorService();
	}
	
	@Bean
	public BookDAO bkDao() {
		return new BookDAO();
	}

	@Bean
	public AuthorDAO authDao() {
		return new AuthorDAO();
	}

	@Bean
	public PublisherDAO pubDao() {
		return new PublisherDAO();
	}
	
	@Bean
	public PlatformTransactionManager txManager(){
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(datasource());
		
		return tx;
	}
	
	@Bean
	public JdbcTemplate template() {
		JdbcTemplate jdbc = new JdbcTemplate();
		jdbc.setDataSource(datasource());
		
		return jdbc;
	}
	
	@Bean
	public BasicDataSource datasource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pwd);

		return ds;
	}
	
	@Bean
	BorrowerDAO brDAO(){
		BorrowerDAO brdao = new BorrowerDAO();
		return brdao;
	}
	
	@Bean
	LibraryBranchDAO lbDAO(){
		LibraryBranchDAO lbDAO = new LibraryBranchDAO();
		return lbDAO;
	}
	
	@Bean
	GenreDAO gDAO(){
		GenreDAO gdao = new GenreDAO();
		return gdao;
	}
	
	@Bean
	BookLoansDAO blDAO(){
		BookLoansDAO bldao = new BookLoansDAO();
		return bldao;
	}
	
	@Bean
	BookCopiesDAO bcDAO(){
		BookCopiesDAO bcdao = new BookCopiesDAO();
		return bcdao;
	}
	
	
	@Bean
	BorrowerServices service1(){
		BorrowerServices service1 = new BorrowerServices();
		return service1;
	}
	
	@Bean
	LibrarianService service2(){
		LibrarianService service2 = new LibrarianService();
		return service2;
	}
}
