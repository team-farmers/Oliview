package com.farmers.oliview.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration //클래스 내부에서 bean 생성 및 설정 (root-context.xml, servlet-context.xml대신 사용)

//@PropertySource : properties 파일의 내용을 이용하겠다 는 어노테이션
// 다른 properties도 추가하고 싶으면 어노테이션을 계속 추가
@PropertySource("classpath:/config.properties")
public class DBConfig {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	@ConfigurationProperties (prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	
	// DataSource: JAVA에서 커넥션 풀을 다루기 위한 인터페이스
	@Bean
	public DataSource dataSource(HikariConfig config) {
	DataSource dataSource = new HikariDataSource(config);
	return dataSource;
	}
	
	
	   //////////////////////////// Mybatis 설정 추가 ////////////////////////////
	   
	   // SqlSessionFactory : SqlSession을 만드는 객체 
	   @Bean
	   public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception{
	      SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
	      
	      sessionFactoryBean.setDataSource(dataSource);
	      
	      // 매퍼 파일이 모여있는 경로 지정
	      sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**.xml"));
	      
	      // 별칭을 지정해야하는 DTO가 모여있는 패키지 지정
	      // -> 해당 패키지에 있는 모든 클래스가 클래스명으로 별칭이 지정됨
//	       sessionFactoryBean.setTypeAliasesPackage("com.farmers.oliview.member.model.dto, com.farmers.oliview.review.model.dto");
	       sessionFactoryBean.setTypeAliasesPackage("com.farmers.oliview");
	      
	      // 마이바티스 설정 파일 경로 지정
	      sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
	      
	      // SqlSession 객체 반환
	      return sessionFactoryBean.getObject();
	   }
	   
	   
	   
	   // SqlSessionTemplate :  기본 SQL 실행 + 트랜잭션 처리
	   @Bean
	   public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sessionFactory) {
	      return new SqlSessionTemplate(sessionFactory);
	   }
	   
	   // DataSourceTransactionManager : 트랜잭션 매니저
	   @Bean
	   public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
	      return new DataSourceTransactionManager(dataSource);
	   }
	
	
	
	
	
}
