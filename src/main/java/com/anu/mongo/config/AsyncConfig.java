
  package com.anu.mongo.config;
  
  import java.util.concurrent.ExecutorService; import
  java.util.concurrent.Executors;
  
  import org.springframework.context.annotation.Bean; import
  org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import
  org.springframework.scheduling.annotation.EnableAsync;
  
  @Configuration
  @EnableAsync 
  public class AsyncConfig {
  
  @Bean
  @Primary
  public ExecutorService taskExecutor() {
	  return Executors.newFixedThreadPool(5);
  } }
 