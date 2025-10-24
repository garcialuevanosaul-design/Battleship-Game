/* Saul Garcia
[CS1101] Comprehensive Lab 2
This work is to be done individually. It is not permitted to.
share, reproduce, or alter any part of this assignment for any
purpose. Students are not permitted to share code, upload
this assignment online in any form, or view/receive/
modifying code written by anyone else. This assignment is part.
of an academic course at The University of Texas at El Paso and
a grade will be assigned for the work produced individually by
the student.
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class CL2_Garcia{

    public static String [][] userGame = new String[11][11];
    public static String [][] actualGame = {{"-", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"},
                                            {"0", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                                            {"1", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                                            {"2", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                                            {"3", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                                            {"4", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                                            {"5", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                                            {"6", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                                            {"7", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                                            {"8", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                                            {"9", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"}};

    public static void main(String[] args) {

        int bomb = 20;

        Scanner input = new Scanner(System.in);

        System.out.println("Get ready for a game of Battleship! ");
        getGame("game3.txt"); //read the file first

        for(int i = 0; i < actualGame.length; i++){ //Read the first actual game
            for(int j = 0; j < actualGame[i].length; j++){
                System.out.print(actualGame[i][j]);
            }
            System.out.println();
        }


        // Read the rows and cols
        try {

            while (bomb > 0) { 
                int selectionRow = -1;
                boolean validRow = false;

                while (!validRow) {
                    System.out.println("Enter row (0-9): ");
                    
                    if(input.hasNextInt()){
                        selectionRow = input.nextInt();

                        if (selectionRow >= 0 && selectionRow <= 9) {
                            validRow = true;
                        } else {
                            System.out.println("Invalid coordinate. Try again.");
                        }
                    }else{
                        System.out.println("Invalid input. Please enter a number. ");
                        input.next();
                    }

                }

                int selectionCol = -1;
                boolean validCol = false;

                while (!validCol) {
                    System.out.println("Enter column (0-9): ");
                    
                    if(input.hasNextInt()){
                        selectionCol = input.nextInt();

                        if (selectionCol >= 0 && selectionCol <= 9) {
                            validCol = true;
                        } else {
                            System.out.println("Invalid coordinate. Try again.");
                        }
                    }else{
                        System.out.println("Invalid input. Please enter a number. ");
                        input.next();
                    }
                }

                bomb = checkTry(selectionRow, selectionCol, bomb); // count the bombs remaining
                usersBattle(); //ask for the actual user Game
                if(isGameWon(bomb) == true){ //if isGameWon true, base on the bombs remaining
                    System.out.println("You sunk all the ships! You win!");
                    printGame();
                    break;
                }

                if(bomb == 0 && (isGameWon(bomb) == false)){ // if the bombs runs out and isGameWon false, you loose
                    System.out.println("Out of tries! Game Over");
                    printGame();
                }
                                   
            }               
        } catch (Exception e) {
            System.out.println("An unexpected error ocurred: " + e);
        }
    }

    public static String [][] getGame(String fileName){
        try {
            File gameFile = new File(fileName);
            Scanner scannerGame = new Scanner(gameFile); // Scanner for read the content of the file
            int row = 0;

            // Iterate each line of the file
            while(scannerGame.hasNext()){
                String readGame = scannerGame.nextLine(); // Scanner for read the line
                Scanner lineScanner = new Scanner(readGame); // Scaner for read each character  of the line
            
            // Iterate each element in the line and store it
                for(int col = 0; col < userGame[row].length; col++){ // Number of row in a column
                    userGame[row][col] = lineScanner.next();
                }
                row++;
            }
        } catch (FileNotFoundException exceptionFile) {
            System.out.println("File not found " + exceptionFile);
        }
        return userGame; // return the userGame global array
    }

    public static void usersBattle(){ //prints the actual game
        for(int i = 0; i < actualGame.length; i++){
            for(int j = 0; j < actualGame[i].length; j++){
                System.out.print(actualGame[i][j]);
            }
            System.out.println();
        }
    }

    public static int checkTry(int row, int col, int bomb){ //I change to Int to return the update bomb value in the main
        String current = actualGame[row + 1][col + 1]; // + 1 because the first row and col are not part of the game

        if(current.equals("X") || current.equals("O")){ // check if the pass row and col values, (selectionRow and selectionCol in the main), are X or O
            System.out.println("Already guessed there!");
        }else{
            if(userGame[row + 1][col + 1].equals("S")){
                actualGame[row + 1][col + 1] = "X";
                System.out.println("Hit!");
            }else{
                actualGame[row + 1][col + 1] = "O";
                System.out.println("Miss!");
            }
        }

        bomb--; // counter of bombs
        System.out.println("Bombs remaining: " + bomb);
        return bomb;
        
    }    
    
    public static boolean isGameWon(int tries){ 
        int countShips = 0;
        for(int i = 0; i < actualGame.length - 1; i++){ 
            for(int j = 0; j < actualGame[i].length - 1; j++){ //count all the S in the original grid
                if(userGame[i + 1][j + 1].equals("S")){
                    countShips++;
                }
            }
        }
        for(int i = 0; i < userGame.length - 1; i++){
            for(int j = 0; j < userGame[i].length - 1; j++){
                if(userGame[i + 1][j + 1].equals("S") && actualGame[i + 1][j + 1].equals("X")){ //look if it match the X in actualGame on the S in userGame
                    countShips--;
                }

            }
        }
        return countShips == 0; //return true if countShips reach to 0
    }
    public static void printGame() {
        // Print the first row, replicating the grid
        System.out.print("-");  
        for (int i = 0; i < actualGame.length - 1; i++) {
            System.out.print(i);
        }
        System.out.println();

        for (int i = 1; i < actualGame.length; i++) {
            System.out.print(i - 1);  // print the next rows 
            for (int j = 1; j < actualGame[i].length; j++) { // print each cell in the grid
                if (actualGame[i][j].equals("X") && userGame[i][j].equals("S")) {
                    System.out.print("X");
                } else if (actualGame[i][j].equals("O")) {
                    System.out.print("O");
                } else if (userGame[i][j].equals("S")) {
                    System.out.print("S");
                } else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }
   
}
