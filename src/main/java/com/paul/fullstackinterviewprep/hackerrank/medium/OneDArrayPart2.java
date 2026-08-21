package com.paul.fullstackinterviewprep.hackerrank.medium;

import java.io.*;
import java.util.*;

public class OneDArrayPart2 {
  
  public static void main(String[] args) {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    
    Scanner scanner = new Scanner(System.in);
    
    int lines = Integer.parseInt(scanner.nextLine()) * 2;
    
    while (lines > 0) {
      
      String firstLine = scanner.nextLine();
      String secondLine = scanner.nextLine();
      
      String[] gameDefinition = firstLine.split(" ");
      String[] boardDefinition = secondLine.split(" ");
      
      int boardSize = Integer.parseInt(gameDefinition[0]);
      int leapSize = Integer.parseInt(gameDefinition[1]);
      
      int[] board = new int[boardSize];
      
      for(int i = 0; i < boardSize; i++) {
        board[i] = Integer.parseInt(boardDefinition[i]);
      }
      
      boolean canProgress = true;
      for(int i = 0; i < boardSize; i++) {
        // Can't land on this space
        if(board[i] == 1) continue;
        
        // See if we can move one or leap to win
        if(i >= boardSize -1 || i + 1 >= boardSize || i + leapSize >= boardSize) {
          break;
        }
        
        // See if we can move one or leap to land on a valid spot
        if(board[i +1] == 0 || board[i + leapSize] == 0) {
          continue;
        } else {
          canProgress = false;
          break;
        }
      }
      
      if(canProgress) {
        System.out.println("YES");
      } else {
        System.out.println("NO");
      }
      
      lines -= 2;
    }
  }
}
