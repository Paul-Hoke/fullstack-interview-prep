package com.paul.fullstackinterviewprep.dependencyinjection;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupApplicationListener {
  
  // Using @Component annotation
  private final DIComponent diComponent;
  
  // Using combination of @Configuration with @Bean annotations
  private final MyBean1 myBean1;
  private final MyBean2 myBean2;
  
  @EventListener
  public void onApplicationReadyEvent(ApplicationReadyEvent event) {
    // Your custom logic here, after everything is ready
    diComponent.printField();
    
    // Overrides BaseBean.getName()
    System.out.println(myBean1.getName());
    
    // Does not override BaseBean.getName()
    System.out.println(myBean2.getName());
    
    System.out.println("Application is fully ready! Performing final warm-up tasks.");
  }
}
