package com.paul.fullstackinterviewprep.dependencyinjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DIConfiguration {
  
  @Bean
  public MyBean1 myBean1() {
    System.out.println("MyBean1 initialized");
    return new MyBean1();
  }
  
  @Bean
  public MyBean2 myBean2() {
    System.out.println("MyBean2 initialized");
    return new MyBean2();
  }
  
}
