//package com.driver.ConfigureDB;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class CustomDb {
//    @Bean
//    public DataSource createDb(){
//        DriverManagerDataSource driverManagerDataSource=new DriverManagerDataSource();
//        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:3306/hotstar");
//        driverManagerDataSource.setUsername("postgres");
//        driverManagerDataSource.setPassword("123456");
//        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
//        return driverManagerDataSource;
//    }
//}
