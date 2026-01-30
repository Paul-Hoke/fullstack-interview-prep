package com.paul.fullstackinterviewprep;

import com.paul.fullstackinterviewprep.dependencyinjection.DIComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FullstackInterviewPrepApplication {

  public static void main(String[] args) {
    SpringApplication.run(FullstackInterviewPrepApplication.class, args);
  }

}
