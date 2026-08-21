package com.paul.fullstackinterviewprep.hackerrank.medium;

import java.util.*;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigDecimalPractice {
  
  public static void main(String[] args) {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    
    Comparator<String> comparable = new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        BigDecimal b1 = new BigDecimal(o1);
        BigDecimal b2 = new BigDecimal(o2);
        
        if(b1.compareTo(b2) == 0) {
          return 1;
        }
        return b2.compareTo(b1);
      }
    };
    
    Scanner in = new Scanner(System.in);
    int lines = Integer.parseInt(in.nextLine());
    
    String[] bigDecimals = new String[lines];
    int currentLine = 0;
    
    while(lines > 0){
      String line = in.nextLine();
      
      bigDecimals[currentLine] = line;
      
      lines--;
      currentLine++;
    }
    
    Arrays.sort(bigDecimals, comparable);
    
    for(int i = 0; i < bigDecimals.length; i++){
      System.out.println(bigDecimals[i]);
    }
  }
}