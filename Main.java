//Imports
import java.util.Scanner;

class Main {
  //Arrays
  int[][] board = new int[3][3];
  //Constants
  final int BLANK = 0;
  final int XMOVE = 1;
  final int OMOVE = 2;
  final int XTURN = 0;
  final int OTURN = 1;
  //Scanners
  Scanner scanner;
  Scanner scanner2;
  //Vars
  String input = "";
  String playerResponse = "";
  int turn = XTURN; //this means x will move first
  int xWins = 0;
  int oWins = 0;
  int ties = 0;
  boolean stillPlaying = true;
  boolean showScore = false;
  //Colored Text
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_WHITE = "\u001B[37m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_GREEN = "\u001B[32m";
  
  public Main() {
    System.out.println(ANSI_GREEN + "\nInstructions: Choose cell by typing a letter and number \tex: a1" + ANSI_WHITE);
    scanner = new Scanner(System.in);

    while (stillPlaying == true) {
      while (checkWin(XMOVE) == false && checkWin(OMOVE) == false && checkTie() == false) { 
        //Print which player's turn and board
        if (turn == 0) {
          System.out.println(ANSI_BLUE + "\nX's Turn:" + ANSI_WHITE);
        } else {
          System.out.println(ANSI_BLUE + "\nO's Turn:" + ANSI_WHITE);
        }
        printBoard();
        
        //Take User Input
        System.out.print("\nEnter the cell you wish to change: ");
        input = scanner.nextLine(); 
  
        //Authenticate user input
        if (input.length() != 2) {             
          System.out.println(ANSI_RED + "Error: Please enter a letter followed by a number" + ANSI_WHITE);
        } else if (input.charAt(0) != 'a' &&   
                 input.charAt(0) != 'b' &&
                 input.charAt(0) != 'c') {
        System.out.println(ANSI_RED + "Error: Row entered must be an a, b, or c." + ANSI_WHITE);
        } else if (input.charAt(1) != '1' &&     
                 input.charAt(1) != '2' &&
                 input.charAt(1) != '3') {
          System.out.println(ANSI_RED + "Error: Column entered must be an 1, 2, or 3." + ANSI_WHITE);
        } else {              //Convert Input to Data
          int row = input.charAt(0) - 'a';
          int column = input.charAt(1) - '1';
          //Print Moves on Board & Switch Turn
          if (board[row][column] == BLANK) {
            if (turn == XTURN) {
              board[row][column] = XMOVE;
              turn = OTURN;
            } else {
              board[row][column] = OMOVE;
              turn = XTURN;
            }
          } else {
            System.out.println(ANSI_RED + "Error: Space not available!" + ANSI_WHITE);
          }
        }
      }

      //Report the Winner
      if (checkWin(XMOVE) == true) {
        xWins += 1;
        System.out.println(ANSI_BLUE + "\nX Wins!" + ANSI_WHITE);
      } else if (checkWin(OMOVE) == true) {
        oWins += 1;
        System.out.println(ANSI_BLUE + "\nO Wins!" + ANSI_WHITE);
      } else if (checkTie() == true) {
        ties += 1;
        System.out.println(ANSI_BLUE + "\nTie!" + ANSI_WHITE);
      }
      showScore = true;
      printBoard();
      stillPlaying = playAgain();
    }
  }

  //Main Method
  public static void main(String[] args) {
    new Main();
  }

  //Prints the board
  public void printBoard() {    
    System.out.println("\n \t1\t2\t3");          //prints 1,2,3 heading
    for (int row=0; row < board.length; row++) {
      String output = (char)('a'+row) + "\t";    //prints a,b,c heading
      for (int column = 0; column < board[0].length; column++) {
        if (board[row][column] == BLANK) {
          output += "\t";
        } else if (board[row][column] == XMOVE) {
          output += "X\t";
        } else if (board[row][column] == OMOVE) {
          output += "O\t";
        }
      }
      System.out.println(output);
    }
    //Shows total X wins, O wins and ties 
    if (showScore == true) {
      System.out.println(ANSI_GREEN + "\nX Wins: " + xWins + "\tO Wins: " + oWins + "\tTies: " + ties + ANSI_WHITE);
      showScore = false;
    }
  }

  //Check For Wins
  public boolean checkWin(int player) {
    //checks columns
    for (int colNum = 0; colNum < board[0].length; colNum++) {
      if (board[colNum][0] == player && board[colNum][1] == player && board[colNum][2] == player) {
        return true;
      }
    }
    //checks rows
    for (int rowNum = 0; rowNum < board.length; rowNum++) {
      if (board[0][rowNum] == player && board[1][rowNum] == player && board[2][rowNum] == player) {
      return true;
      }
    }
    //checks diagonals
    if (board[0][0] == player && board[1][1] == player && board[2][2] == player || 
       board[0][2] == player && board[1][1] == player && board[2][0] == player) {
      return true;
    } 
    return false;  //means nobody won
  }

  //check for a tie
  public boolean checkTie() {
    for (int row = 0; row < board.length; row++) {
      for (int column = 0; column < board[0].length; column++) {
        if (board[row][column] == BLANK) {
          return false;
        } 
      }
    }  
    return true;
  }

  //Asks if player wants to play again
  public boolean playAgain() {
    while (true) {
      System.out.print("Do you want to play again? Enter y or n: ");
      scanner2 = new Scanner(System.in);
      playerResponse = scanner2.nextLine();
      //Decifers User Input
      if (playerResponse.charAt(0) == 'y') { 
        turn = XTURN;
        clearBoard();
        return true;
      } else if (playerResponse.charAt(0) == 'n') {
        System.out.println("Thanks for Playing :)");
        return false;
      } else {
        System.out.println(ANSI_RED + "Error: Invalid response." + ANSI_WHITE);
      }
    }  
  }

  //Clears the board
  public void clearBoard() {
    for (int row = 0; row < board.length; row++) {
      for (int column = 0; column < board[0].length; column++) {
        board[row][column] = BLANK;
      } 
    }
  }  
  
}