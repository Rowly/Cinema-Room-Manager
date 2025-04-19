package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] cinema;
        int numSoldTickets = 0;
        int currentIncome = 0;
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        cinema = makeLayout(rows, seats);
        boolean resume = true;
        while (resume) {
            printMenu();
            int option = scanner.nextInt();

            switch (option) {
                case 1: {
                    printSeating(cinema);
                    break;
                }
                case 2: {
                    boolean seatValid = false;
                    while (!seatValid) {
                        System.out.println("Enter a row number:");
                        int row = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int seat = scanner.nextInt();
                        if (validateSeatNumber(rows, seats, row, seat) && updateSeatingPlan(cinema, row, seat)) {
                            numSoldTickets += 1;
                            int ticketPrice = calculateTicketPrice(cinema.length, cinema[0]. length, row, seat);
                            currentIncome += ticketPrice;
                            System.out.println("Ticket price: $" + ticketPrice);
                            seatValid = true;
                        }
                    }
                    break;
                }
                case 3: {
                    printStatistics(cinema, numSoldTickets, currentIncome);
                    break;
                }
                case 0: {
                    resume = false;
                    break;
                }
                default:
                    System.out.println("Only options 1 2 0 are valid input.");
            }
        }
    }

    private static char[][] makeLayout(int rows, int seats) {
        char[][] layout = new char[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                layout[i][j] = 'S';
            }
        }
        return layout;
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private static void printSeating(char[][] layout) {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int j = 1; j <= layout[0].length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        for (int i = 0; i < layout.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < layout[i].length; j++) {
                System.out.print(layout[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean updateSeatingPlan(char[][] layout, int row, int seat) {
        if (layout[row - 1][seat - 1] == 'B') {
            System.out.println("That ticket has already been purchased!");
            return false;
        }
        layout[row - 1][seat - 1] = 'B';

        return true;
    }

    private static boolean validateSeatNumber(int rows, int seats, int row, int seat) {
        if (row >= 1 && row <= rows && seat >= 1 && seat <= seats) {
            return true;
        } else {
            System.out.println("Wrong input!");
            return false;
        }
    }

    private static int calculateTicketPrice(int rows, int seats, int row, int seat) {
        int result;
        int totalSeats = rows * seats;
        int higherPrice = 10;
        int lowerPrice = 8;

        if (totalSeats <= 60) {
            result = higherPrice;
        } else {
            int frontHalf = rows / 2;
            int backHalf = rows - frontHalf;
            if (row <= frontHalf) {
                result = higherPrice;
            } else {
                result = lowerPrice;
            }
        }
        return result;
    }

    private static int calculateProfit(int rows, int seats) {
        int higherPrice = 10;
        int lowerPrice = 8;
        int total = 0;

        int totalSeats = rows * seats;

        if (totalSeats <= 60) {
            total = totalSeats * higherPrice;
        } else {
            int frontHalf = rows / 2;
            int backHalf = rows - frontHalf;
            total = (frontHalf * seats * higherPrice) + (backHalf * seats * lowerPrice);
        }
        return total;
    }

    private static int calculateTotalPossibleIncome(int rows, int seats) {
        int totalPrice = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seats; j++) {
                totalPrice += calculateTicketPrice(rows, seats, i, j);
            }
        }
        return totalPrice;
    }

    private static int calculateTotalSeats(char [][] layout) {
        int totalSeat = 0;
        for (int i = 1; i <= layout.length; i++) {
            for (int j = 1; j <= layout[0].length; j++) {
                totalSeat += 1;
            }
        }
        return totalSeat;
    }

    private static String calculateSoldSeatsPercentage(int soldSeats, int totalSeats) {
        if (totalSeats == 0) {
            return "0.00"; // Prevent division by zero
        }
        double percentage = (soldSeats * 100.0) / totalSeats;
        return String.format("%.2f", percentage);
    }

    private static void printStatistics(char[][] cinema, int numSoldTickets, int currentIncome) {
        System.out.println();
        System.out.println("Number of purchased tickets: " + numSoldTickets);
        int totalSeats = calculateTotalSeats(cinema);
        System.out.println("Percentage: " + calculateSoldSeatsPercentage(numSoldTickets, totalSeats) + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + calculateTotalPossibleIncome(cinema.length, cinema[0].length));
    }
}