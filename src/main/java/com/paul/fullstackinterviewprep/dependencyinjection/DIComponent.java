package com.paul.fullstackinterviewprep.dependencyinjection;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DIComponent {

  String field = "this is a field";
  
  public void printField() {
    System.out.println(field);
  }

}
