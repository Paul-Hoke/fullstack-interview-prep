package com.paul.fullstackinterviewprep.hackerrank.medium;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputAndPatternMatch {
  
  public static void main(String[] args) {
    
    Scanner scanner = new Scanner(System.in);
    String regex = "\\b(\\w+)\\s+";
    Pattern pattern = Pattern.compile(regex);
    
    int lines = Integer.parseInt(scanner.nextLine());
    
    while (lines > 0) {
      
      String input = scanner.nextLine();
      
      Matcher matcher = pattern.matcher(input);
      
      while(matcher.find()) {
        
      }
      
      
      lines--;
    }
  }
}
