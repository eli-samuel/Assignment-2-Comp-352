import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

public class MagicBoard {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Getting the size of the board from the user
        System.out.print("Input size of board: ");
        int D = in.nextInt();
        in.nextLine();

        // Making sure the input was valid
        while (D > 20 && D < 5) {
            System.out.print("Size out of bounds. Please input a number between 5 and 20: ");
            D = in.nextInt();
        }

        System.out.println("Size is " + D + " x " + D + " (press PERIOD to continue)");
        in.next();

        int[][] board = new int[D][D];

        int diff = 1;
        int[] pos = new int[2];

        // Asking the user to select the starting position
        do {
            clearScreen();
            System.out.println("Select starting position (press ENTER to cycle choices, PERIOD to confirm):");

            switch (diff) {
                case 1:
                    System.out.println("\tTop left <<\n\tTop right\n\tBottom left\n\tBottom right");
                    pos[0] = 0;
                    pos[1] = 0;
                    System.out.println(printBoard(pos, board));
                    break;
                case 2:
                    System.out.println("\tTop left\n\tTop right <<\n\tBottom left\n\tBottom right");
                    pos[0] = 0;
                    pos[1] = D-1;
                    System.out.println(printBoard(pos, board));
                    break;
                case 3:
                    System.out.println("\tTop left\n\tTop right\n\tBottom left <<\n\tBottom right");
                    pos[0] = D-1;
                    pos[1] = 0;
                    System.out.println(printBoard(pos, board));
                    break;
                case 4:
                    System.out.println("\tTop left\n\tTop right\n\tBottom left\n\tBottom right <<");
                    pos[0] = D-1;
                    pos[1] = D-1;
                    System.out.println(printBoard(pos, board));
                    break;
            }

            if (diff == 4) diff = 1;
            else diff++;
        } while (in.nextLine().length() == 0);

        // Filling the board with random numbers and one zero
        Random rand = new Random();
        boolean hasZero = false;
        for (int i=0; i<D; i++) {
            for (int j=0; j<D; j++) {
                int k = rand.nextInt(D);
                if (hasZero == false) {
                    board[i][j] = k;
                    if (k == 0 && (i != pos[0] && j != pos[1])) hasZero = true;
                    else {
                        while (k == 0) { k = rand.nextInt(D); }
                        board[i][j] = k;
                    }
                }
                else {
                    while (k == 0) { k = rand.nextInt(D); }
                    board[i][j] = k;
                }
            }
        }
        if (!hasZero) board[D-2][D-2] = 0;

        // Creating a check array to save where the program has visited
        boolean[][] check = new boolean[D][D];
        for (int i=0; i<check.length; i++) {
            for (int j=0; j<check.length; j++) {
                check[i][j] = false;
            }
        }
        check[pos[0]][pos[1]] = true;


        System.out.println("Here is your board:\n\n" + printBoard(pos, board));
        System.out.println("Are you ready to begin? (PERIOD to continue)");
        in.next();

        System.out.println(move(pos, board, check));
    }

    /*
     * This method recursively finds the path to the final position. It attempts
     * to go in every direction until it either finds the goal position, a
     * position it has already been to, or a position where it has no places to
     * go from there.
     *
     * @param int[] startPos the position of the user
     * @param int[][] board the board array
     * @param boolean[][] check the visited array
     * @return boolean if you can move to the final position
     */
    public static boolean move(int[] startPos, int[][] board, boolean[][] check) {

        int moveValue = board[startPos[0]][startPos[1]];
        if (moveValue == 0) { // base case
            System.out.println(printBoard(startPos, board));
            return true;
        }

        // from startPos, move in a direction moveValue and keep going recursively
        if (startPos[1] + moveValue < board.length) { // can move east
            int[] pos = {startPos[0], startPos[1]+moveValue};
            if (!check[pos[0]][pos[1]]) {
                check[pos[0]][pos[1]] = true;
                if (move(pos, board, check)) {
                    return true;
                }

            }

        }

        if (startPos[0] + moveValue < board.length) { // can move south
            int[] pos = {startPos[0]+moveValue, startPos[1]};
            if (!check[pos[0]][pos[1]]) {
                check[pos[0]][pos[1]] = true;
                if (move(pos, board, check)) {
                    return true;
                }

            }

        }

        if (startPos[1] - moveValue < board.length && startPos[1] - moveValue >= 0) { // can move west
            int[] pos = {startPos[0], startPos[1]-moveValue};
            if (!check[pos[0]][pos[1]]) {
                check[pos[0]][pos[1]] = true;
                if (move(pos, board, check)) {
                    return true;
                }

            }

        }

        if (startPos[0] - moveValue < board.length && startPos[0] - moveValue >= 0) { // can move north
            int[] pos = {startPos[0]-moveValue, startPos[1]};
            if (!check[pos[0]][pos[1]]) {
                check[pos[0]][pos[1]] = true;
                if (move(pos, board, check)) {
                    return true;
                }

            }

        }

        //System.out.println(printBoard(startPos, board));
        return false;

    }

    /*
     * This method prints an 2d array (in this case the board)
     * @param int[] pos the position of the user
     * @param int[][] arr the board
     * @return void
     */
    private static String printBoard(int[] pos, int[][] arr) {
        String s = "";

        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[i].length; j++) {
                if (i == pos[0] && j == pos[1]) {
                    s += ("|" + arr[i][j] + "| ");
                }
                else s += (" " + arr[i][j] + "  ");

            }
            s += ("\n");
        }
        s += ("============================");

        return s;
    }

    /*
     * This method clears the console for prettier presenting
     * @return void
     */
    public static void clearScreen() {
        try { new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); }
        catch (InterruptedException e) { System.out.println(e.getMessage()); }
        catch (IOException e) { System.out.println(e.getMessage()); }
    }
}
