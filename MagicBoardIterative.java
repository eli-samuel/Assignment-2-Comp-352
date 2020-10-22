import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.util.Stack;
import java.util.Arrays;

public class MagicBoardIterative {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Input size of board: ");
        int D = in.nextInt();

        while (D > 20 && D < 5) {
            System.out.print("Size out of bounds. Please input a number between 5 and 20: ");
            D = in.nextInt();
        }


        System.out.println("Size is " + D + " x " + D + " (press PERIOD to continue)");
        in.next();

        int[][] board = new int[D][D];

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

    public static boolean move(int[] startPos, int[][] board, boolean[][] check) {
        Stack<int[]> path = new Stack<int[]>();
        path.push(startPos);

        int moveValue = board[startPos[0]][startPos[1]];

        while (!path.isEmpty()) {

            System.out.println(Arrays.deepToString(path.toArray()));

            moveValue = board[path.peek()[0]][path.peek()[1]];

            if (moveValue == 0) {
                System.out.println("\n\n" + printBoard(path.peek(), board));
                return true;
            }

            if (path.peek()[1] + moveValue < board.length) { // can move east
                int[] pos = {path.peek()[0], path.peek()[1]+moveValue};
                if (!check[pos[0]][pos[1]]) {
                    check[pos[0]][pos[1]] = true;
                    path.push(pos);
                    continue;
                }
            }

            if (path.peek()[0] + moveValue < board.length) { // can move south
                int[] pos = {path.peek()[0]+moveValue, path.peek()[1]};
                if (!check[pos[0]][pos[1]]) {
                    check[pos[0]][pos[1]] = true;
                    path.push(pos);
                    continue;
                }
            }

            if (path.peek()[1] - moveValue >= 0) { // can move west
                int[] pos = {path.peek()[0], path.peek()[1]-moveValue};
                if (!check[pos[0]][pos[1]]) {
                    check[pos[0]][pos[1]] = true;
                    path.push(pos);
                    continue;
                }
            }

            if (path.peek()[0] - moveValue >= 0) { // can move north
                int[] pos = {path.peek()[0]-moveValue, path.peek()[1]};
                if (!check[pos[0]][pos[1]]) {
                    check[pos[0]][pos[1]] = true;
                    path.push(pos);
                    continue;
                }
            }

            int[] popped = path.pop();
        }
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
