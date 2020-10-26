import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cinema {

    final static Scanner scanner = new Scanner(System.in);

    public static int input(String text) {
        System.out.print(text);
        try { return scanner.nextInt(); }
        catch (InputMismatchException e) { return -1; }
    }

    public static String printMenu() {
        return ("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit\n");
    }

    public static void printSeatArrangement(char[][] seats) {
        System.out.println("Cinema:");
        for (int i = 0; i < seats.length + 1; i++) {
            for (int j = 0; j < seats[0].length + 1; j++) {
                if (i == 0 && j == 0) System.out.print("  ");
                if (i != 0 && j == 0) System.out.print(i + " ");
                if (i == 0 && j != 0) System.out.print(j + " ");
                if (i != 0 && j != 0) System.out.print(seats[i-1][j-1] + " ");
            }
            System.out.println();
        }
    }

    public static char[][] getSeats(int rows, int cols) {
        char[][] seats = new char[rows][cols];
        for (char[] seat : seats) Arrays.fill(seat, 'S');
        return seats;
    }

    public static void buyTicket(char[][] seats) {
        int row = getNum("Enter a row number:\n", seats.length);
        int col = getNum("Enter a seat number in that row:\n", seats[0].length);
        int price;

        while(true) {
            if (seats[row - 1][col - 1] == 'B') {
                System.out.println("That ticket has already been purchased");
                row = getNum("Enter a row number:\n", seats.length);
                col = getNum("Enter a seat number in that row:\n", seats[0].length);
            } else break;
        }

        if (seats.length * seats[0].length < 60) price = 10;
        else if (seats.length / 2 >= row) price = 10;
        else price = 8;

        System.out.println("Ticket price: $" + price);
        seats[row - 1][col - 1] = 'B';
    }

    public static int getNum(String text, int m) {
        int num = input(text);
        while (true) {
            if ((num >=0 && num <= m) || (num >=0 && m == -1)) return num;
            System.out.println("Wrong input!");
            num = input(text);
        }
    }

    public static void printStatistics(char[][] seats) {
        int purchased = getNumPurchased(seats);
        int total = seats.length * seats[0].length;
        System.out.printf("Number of purchased tickets: %d\n", purchased);
        System.out.printf("Percentage: %.2f%%\n", getPercentage(purchased, total));
        System.out.printf("Current income: $%d\n", getIncome(seats, true));
        System.out.printf("Total income: $%d\n", getIncome(seats, false));
    }

    public static int getNumPurchased(char[][] seats) {
        int count = 0;
        for (char[] seat : seats) {
            for (char c : seat) if (c == 'B') count++;
        }
        return count;
    }

    public static double getPercentage(int purchased, int total) {
        return (double) (100 * purchased) / total;
    }

    public static int getIncome(char[][] seats, boolean current) {
        int row = seats.length;
        int col = seats[0].length;
        if (row * col < 60 && !current) return row * col * 10;
        int income = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (seats[i][j] == 'B' && current) {
                    if (i < row / 2) income += 10;
                    else income += 8;
                } else if (!current) {
                    if (i < row / 2) income += 10;
                    else income += 8;
                }
            }
        }
        return income;
    }

    public static void runCinema() {
        int rows = getNum("Enter the number of rows:\n", -1);
        int cols = getNum("Enter the number of seats in each row:\n", -1);
        char[][] seats = getSeats(rows, cols);
        System.out.println();

        int option = input(printMenu());
        System.out.println();

        while (option != 0) {
            if (option == 1) printSeatArrangement(seats);
            if (option == 2) buyTicket(seats);
            if (option == 3) printStatistics(seats);
            System.out.println();

            option = input(printMenu());
            System.out.println();
        }
    }

    public static void main(String[] args) {
        runCinema();
    }
}