package com.paul.fullstackinterviewprep.hackerrank.medium;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class JavaRegex {
  
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while(in.hasNext()){
      String IP = in.next();
      System.out.println(IP.matches(new MyRegex().pattern));
    }
    
  }
  
  private static class MyRegex {
    String pattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9])$";
  }
}
