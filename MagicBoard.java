import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

public class MagicBoard {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Input size of board: ");
        final int D = in.nextInt();

        System.out.println("Size is " + D + " x " + D + " (press PERIOD to continue)");
        in.next();

        int[][] board = new int[D][D];

        Random rand = new Random();
        boolean hasZero = false;
        for (int i=0; i<D; i++) {
            for (int j=0; j<D; j++) {
                int k = rand.nextInt(D);
                if (hasZero == false) {
                    board[i][j] = k;
                    if (k == 0 && (i != 0 && j != 0) && (i != 0 && j != D-1) && (i != D-1 && j != D-1) && (i != D-1 && j != 0)) hasZero = true;
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

        int diff = 1;
        int[] pos = new int[2];

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

        boolean[][] check = new boolean[D][D];
        for (int i=0; i<check.length; i++) {
            for (int j=0; j<check.length; j++) {
                check[i][j] = false;
            }
        }

        check[pos[0]][pos[1]] = true;

        System.out.println("Are you ready to clear screen and begin? (PERIOD to continue)");
        in.next();
        clearScreen();

        System.out.println(move(pos, board, check));
    }

    public static boolean move(int[] startPos, int[][] board, boolean[][] check) {

        int moveValue = board[startPos[0]][startPos[1]];
        if (moveValue == 0) {
            System.out.println(printBoard(startPos, board));
            return true; // base case
        }

        // from startPos, move in a direction moveValue and keep going recursively
        if (startPos[1] + moveValue < board.length) { // can move east
            int[] pos = {startPos[0], startPos[1]+moveValue};
            if (!check[pos[0]][pos[1]]) {
                check[pos[0]][pos[1]] = true;
                System.out.println(printBoard(startPos, board));
                System.out.println("Attempting to move from (" + startPos[0] + ", " + startPos[1] + ") to (" + pos[0] + ", " + pos[1] + ").");
                if (move(pos, board, check)) {
                    return true;
                }
                else {
                    System.out.println("Unable to move from (" + pos[0] + ", " + pos[1] + ").");
                }
            }
            else {
                System.out.println("There is no path east");
                System.out.println("============================");
            }
        }

        if (startPos[0] + moveValue < board.length) { // can move south
            int[] pos = {startPos[0]+moveValue, startPos[1]};
            if (!check[pos[0]][pos[1]]) {
                check[pos[0]][pos[1]] = true;
                System.out.println(printBoard(startPos, board));
                System.out.println("Attempting to move from (" + startPos[0] + ", " + startPos[1] + ") to (" + pos[0] + ", " + pos[1] + ").");
                if (move(pos, board, check)) {
                    return true;
                }
                else {
                    System.out.println("Unable to move from (" + pos[0] + ", " + pos[1] + ").");
                }
            }
            else {
                System.out.println("There is no path south");
                System.out.println("============================");
            }
        }

        if (startPos[1] - moveValue < board.length && startPos[1] - moveValue >= 0) { // can move west
            int[] pos = {startPos[0], startPos[1]-moveValue};
            if (!check[pos[0]][pos[1]]) {
                check[pos[0]][pos[1]] = true;
                System.out.println(printBoard(startPos, board));
                System.out.println("Attempting to move from (" + startPos[0] + ", " + startPos[1] + ") to (" + pos[0] + ", " + pos[1] + ").");
                if (move(pos, board, check)) {
                    return true;
                }
                else {
                    System.out.println("Unable to move from (" + pos[0] + ", " + pos[1] + ").");
                }
            }
            else {
                System.out.println("There is no path west");
                System.out.println("============================");
            }
        }

        if (startPos[0] - moveValue < board.length && startPos[0] - moveValue >= 0) { // can move north
            int[] pos = {startPos[0]-moveValue, startPos[1]};
            if (!check[pos[0]][pos[1]]) {
                check[pos[0]][pos[1]] = true;
                System.out.println(printBoard(startPos, board));
                System.out.println("Attempting to move from (" + startPos[0] + ", " + startPos[1] + ") to (" + pos[0] + ", " + pos[1] + ").");
                if (move(pos, board, check)) {
                    return true;
                }
                else {
                    System.out.println("Unable to move from (" + pos[0] + ", " + pos[1] + ").");
                }
            }
            else {
                System.out.println("There is no path north");
                if (!check[pos[0]][pos[1]]) System.out.println("Already been to this area.");
                System.out.println("============================");
            }
        }
        
        check[startPos[0]][startPos[1]] = false;
        System.out.println(printBoard(startPos, board));
        return false;

    }

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

    private static String printBoard(int[] pos, boolean[][] arr) {
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

    public static void clearScreen() {
        try { new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); }
        catch (InterruptedException e) { System.out.println(e.getMessage()); }
        catch (IOException e) { System.out.println(e.getMessage()); }
    }
}
