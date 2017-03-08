package ps4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by eiros_000 on 1/3/2017.
 */
public class TicTacToe {

    public static void main(String[] args) {

        String[][] grid = new String[][]{{"A1","A2","A3"},{"B1","B2","B3"},{"C1","C2","C3"}};
        boolean isThereAWinner = false;
        int turnNumber = 0;

        ArrayList<String> validMoves = new ArrayList<>();
        validMoves.add("A1");
        validMoves.add("A2");
        validMoves.add("A3");
        validMoves.add("B1");
        validMoves.add("B2");
        validMoves.add("B3");
        validMoves.add("C1");
        validMoves.add("C2");
        validMoves.add("C3");

//        TicTacToeGame game = new TicTacToeGame();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(3333);
            serverSocket.setSoTimeout(10000);

            System.out.println("Game is currently being set up...");

            Socket player1 = serverSocket.accept();
            System.out.println("Player 1 has entered");
            Socket player2 = serverSocket.accept();
            System.out.println("Player 2 has entered");


            BufferedReader[] input = new BufferedReader[2];
            input[0] = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            input[1] = new BufferedReader(new InputStreamReader(player2.getInputStream()));
            PrintWriter[] output = new PrintWriter[2];
            output[0] = new PrintWriter(player1.getOutputStream(),true);
            output[1] = new PrintWriter(player2.getOutputStream(),true);

            for(int i=0;i<2;i++) {
                output[i].println("Game begins!");
                output[i].println("Sending grid...");
                for(String gridOutput:displayGrid(grid)) {
                    output[i].println(gridOutput);
                }
                output[i].println("Grid sent!");
            }

            while(!isThereAWinner) {
                int currentPlayer = turnNumber % 2;
                boolean validMoveEntered = false;

                while (!validMoveEntered) {
                    output[currentPlayer].println("Please select a spot to place your symbol");

                    String squareInput = input[currentPlayer].readLine();
                    if (validMoves.contains(squareInput)) {

                        char[] inputCoord = squareInput.toCharArray();
                        int iVal = -1;
                        if (Character.toString(inputCoord[0]).matches("A")) {
                            iVal = 0;
                        }
                        if (Character.toString(inputCoord[0]).matches("B")) {
                            iVal = 1;
                        }
                        if (Character.toString(inputCoord[0]).matches("C")) {
                            iVal = 2;
                        }
                        int jVal = Integer.parseInt((Character.toString(inputCoord[1]))) - 1;

                        if (grid[iVal][jVal].equals("O ") || grid[iVal][jVal].equals("X ")) {
                            output[currentPlayer].println("Invalid input, please try again");
                        } else {
                            grid = updateGrid(currentPlayer, iVal, jVal, grid);
                            validMoveEntered = true;
                        }
                    } else {
                        output[currentPlayer].println("Invalid input, please try again");
                    }
                }

                if (didIWin(currentPlayer,grid)) {
                    isThereAWinner = true;
                    for(int i=0;i<2;i++) {
                        output[i].println("Sending grid...");
                        for(String gridOutput:displayGrid(grid)) {
                            output[i].println(gridOutput);
                        }
                        output[i].println("Grid sent!");
                    }
                    output[currentPlayer].println("You are the winner!");
                    output[(currentPlayer+1)%2].println("Sorry, you have lost");
                }

                for(int i=0;i<2;i++) {
                    output[i].println("Sending grid...");
                    for(String gridOutput:displayGrid(grid)) {
                        output[i].println(gridOutput);
                    }
                    output[i].println("Grid sent!");
                }

                turnNumber++;
            }

            for(int i=0;i<2;i++) {
                input[i].close();
                output[i].close();
            }
            player1.close();
            player2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> displayGrid(String[][] grid) {
        ArrayList<String> output = new ArrayList<>();
        output.add(" " + grid[0][0] + " | " + grid[0][1] + " | " + grid[0][2]);
        output.add("--------------");
        output.add(" " + grid[1][0] + " | " + grid[1][1] + " | " + grid[1][2]);
        output.add("--------------");
        output.add(" " + grid[2][0] + " | " + grid[2][1] + " | " + grid[2][2]);
//        String output =
//                " " + grid[0][0] + " | " + grid[0][1] + " | " + grid[0][2] + "\n"
//                + "--------------" + "\n"
//                + " " + grid[1][0] + " | " + grid[1][1] + " | " + grid[1][2] + "\n"
//                + "--------------" + "\n"
//                + " " + grid[2][0] + " | " + grid[2][1] + " | " + grid[2][2];
        return output;
    }

    public static String[][] updateGrid(int turnNumber, int iVal, int jVal, String[][] grid) {
        String symbol = "";
        String[][] tempGrid = grid;
        int currentPlayer = (turnNumber%2) + 1;
        switch (currentPlayer) {
            case 1:
                symbol = "X ";
                break;
            case 2:
                symbol = "O ";
                break;
            default:
                System.out.println("Error updating");
                break;
        }

        if (!tempGrid[iVal][jVal].equals("X ") && !tempGrid[iVal][jVal].equals("O ")) {
            tempGrid[iVal][jVal] = symbol;
        }

        return tempGrid;
    }

    public static boolean didIWin(int turnNumber, String[][] grid){
        String symbol = "";
        int currentPlayer = (turnNumber%2) + 1;


        switch (currentPlayer) {
            case 1:
                symbol = "X ";
                break;
            case 2:
                symbol = "O ";
                break;
            default:
                System.out.println("Error checking");
                break;
        }
        for (int i=0; i<3; i++) {
            // Check all horizontals
            if (grid[i][0].equals(symbol) && grid[i][1].equals(symbol) && grid[i][2].equals(symbol)) {
                return true;
            }
            // Check all verticals
            if (grid[0][i].equals(symbol) && grid[1][i].equals(symbol) && grid[2][i].equals(symbol)) {
                return true;
            }
        }
        // Check \ diagonal
        if (grid[0][0].equals(symbol) && grid[1][1].equals(symbol) && grid[2][2].equals(symbol)) {
            return true;
        }
        // Check / diagonal
        if (grid[2][0].equals(symbol) && grid[1][1].equals(symbol) && grid[0][2].equals(symbol)) {
            return true;
        }
        return false;
    }

}

/*
class TicTacToeGame {

    private String[][] grid = new String[][]{{"A1","A2","A3"},{"B1","B2","B3"},{"C1","C2","C3"}};
    private boolean isThereAWinner = false;
    private int turnNumber = 0;

    public TicTacToeGame(){
        displayGrid();
        while(!isThereAWinner) {
            enterMove();
            if(didIWin()) {
                this.isThereAWinner = true;
                System.out.println("You are the winner!");
            }
            turnNumber ++;
            displayGrid();
        }
    }

    public void enterMove() {
        Scanner scan =  new Scanner(System.in);
        ArrayList<String> validMoves = new ArrayList<String>();
        validMoves.add("A1");
        validMoves.add("A2");
        validMoves.add("A3");
        validMoves.add("B1");
        validMoves.add("B2");
        validMoves.add("B3");
        validMoves.add("C1");
        validMoves.add("C2");
        validMoves.add("C3");

        System.out.println("Please select a spot to place your symbol");
        String input = scan.next();

        if(validMoves.contains(input)){
//            System.out.println(input);
//            String[] inputCoord = input.split("(?!^)");
            char[] inputCoord = input.toCharArray();
//            System.out.println(inputCoord);
            int iVal = -1;
//            System.out.println("OK");
            if (Character.toString(inputCoord[0]).matches("A")) {
                iVal = 0;
            }
            if (Character.toString(inputCoord[0]).matches("B")) {
                iVal = 1;
            }
            if (Character.toString(inputCoord[0]).matches("C")) {
                iVal = 2;
            }
            int jVal = Integer.parseInt((Character.toString(inputCoord[1]))) - 1;
            updateGrid(iVal,jVal);
        } else {
            System.out.println("Invalid input, please try again");
            enterMove();
        }
    }

    public void updateGrid(int iVal, int jVal) {
        String symbol = "";
        String[][] tempGrid = this.grid;
        int currentPlayer = (this.turnNumber%2) + 1;
        switch (currentPlayer) {
            case 1:
                symbol = "X ";
                break;
            case 2:
                symbol = "O ";
                break;
            default:
                System.out.println("Error updating");
                break;
        }

        if (!tempGrid[iVal][jVal].equals("X ") && !tempGrid[iVal][jVal].equals("O ")) {
            tempGrid[iVal][jVal] = symbol;
        }
    }

    public void displayGrid() {
        System.out.println(" " + grid[0][0] + " | " + grid[0][1] + " | " + grid[0][2]);
        System.out.println("--------------");
        System.out.println(" " + grid[1][0] + " | " + grid[1][1] + " | " + grid[1][2]);
        System.out.println("--------------");
        System.out.println(" " + grid[2][0] + " | " + grid[2][1] + " | " + grid[2][2]);
    }

    public String displayGrid() {
        String output =
                " " + grid[0][0] + " | " + grid[0][1] + " | " + grid[0][2]
                + "--------------"
                + " " + grid[1][0] + " | " + grid[1][1] + " | " + grid[1][2]
                + "--------------"
                + " " + grid[2][0] + " | " + grid[2][1] + " | " + grid[2][2];
        return output;
    }

    public boolean didIWin(){
        String symbol = "";
        int currentPlayer = (this.turnNumber%2) + 1;


        switch (currentPlayer) {
            case 1:
                symbol = "X ";
                break;
            case 2:
                symbol = "O ";
                break;
            default:
                System.out.println("Error checking");
                break;
        }
        for (int i=0; i<3; i++) {
            // Check all horizontals
            if (grid[i][0].equals(symbol) && grid[i][1].equals(symbol) && grid[i][2].equals(symbol)) {
                return true;
            }
            // Check all verticals
            if (grid[0][i].equals(symbol) && grid[1][i].equals(symbol) && grid[2][i].equals(symbol)) {
                return true;
            }
        }
        // Check \ diagonal
        if (grid[0][0].equals(symbol) && grid[1][1].equals(symbol) && grid[2][2].equals(symbol)) {
            return true;
        }
        // Check / diagonal
        if (grid[2][0].equals(symbol) && grid[1][1].equals(symbol) && grid[0][2].equals(symbol)) {
            return true;
        }
        return false;
    }
}
*/