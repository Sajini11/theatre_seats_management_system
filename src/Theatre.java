import java.io.*;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


public class Theatre {
    private static int[][] seats = new int[3][];  // Initialize seats array

    private static  final int[] ROW_LENGTHS = {12, 16, 20};  // Array that stores the number of seats in each row

    //private static Scanner input = new Scanner(System.in);
    private static ArrayList<Ticket> tickets = new ArrayList<>();  // ArrayList that stores Ticket objects

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < seats.length; i++) {  // Initialize seats array
            seats[i] = new int[ROW_LENGTHS[i]];
        }



        System.out.println("Welcome to the New Theatre");
        System.out.println("-------------------------------------------------");

        while (true) {
            System.out.println("1) Buy a ticket");
            System.out.println("2) Print seating area");
            System.out.println("3) Cancel ticket");
            System.out.println("4) List available seats");
            System.out.println("5) Save to file");
            System.out.println("6) Load from file");
            System.out.println("7) Print ticket information and total price");
            System.out.println("8) Sort tickets by price");
            System.out.println("0) Quit");
            System.out.println("-------------------------------------------------");
            System.out.print("Enter option: ");

            try {
                int option = input.nextInt();

                switch (option) {
                    case 0:
                        System.out.println("\nThank you for using New Theatre System, Have a nice day!");
                        return;
                    case 1:
                        buy_ticket();
                        break;
                    case 2:
                        print_seating_area();
                        break;
                    case 3:
                        cancel_ticket();
                        break;
                    case 4:
                        show_available();
                        break;
                    case 5:
                        save();
                        break;
                    case 6:
                        load();
                        break;
                    case 7:
                        show_tickets_info();
                        break;
                    case 8:
                        sort_tickets();
                        break;
                    default:
                        System.out.println("Invalid option.\n");
                        break;
                }
                // when user enters a letter or something not numbers as input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.\n");
                input.nextLine(); // clear the input buffer
            }
        }

    }

    // method for buy tickets
    private static void buy_ticket() {

        Scanner input = new Scanner(System.in);    // Get row number from user

        int row = -1;
        while (row < 0 || row >= seats.length) {
            System.out.print("\nEnter row number (1-" + seats.length + ")  : ");
            if (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
                continue;
            }
            row = input.nextInt() - 1;   // Subtract 1 from the row number to convert from 1-indexed to 0-indexed
            if (row < 0 || row >= seats.length) {
                System.out.println("Invalid row number. Please try again.");
            }
        }

        // Get seat number from user
        int seat = -1;
        while (seat < 0 || seat >= ROW_LENGTHS[row]) {
            System.out.print("Enter seat number (1-" + ROW_LENGTHS[row] + "): ");
            if (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
                continue;
            }
            seat = input.nextInt() - 1;
            if (seat < 0 || seat >= ROW_LENGTHS[row]) {
                System.out.println("Invalid seat number. Please try again.");
            } else if (seats[row][seat] == 1) {
                System.out.println("Seat already sold. Please try again.");
                seat = -1;
            }
        }

        // Get person details from user
        System.out.print("Enter name    : ");
        String name = input.next();

        System.out.print("Enter surname : ");
        String surname = input.next();

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";   //email validation -https://www.javatpoint.com/java-email-validation
        Pattern emailPattern = Pattern.compile(emailRegex);
        String email = "";
        while (!emailPattern.matcher(email).matches()) {   // if email is not in correct format, ask user to enter email until enter the correct format email
            System.out.print("Enter email   : ");
            email = input.next();
            if (!emailPattern.matcher(email).matches()) {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        // Get ticket price from user
        double price = -1;
        while (price < 0) {
            System.out.print("Enter price   : ");
            if (!input.hasNextDouble()) {  //check the user entered price is number or not
                System.out.println("Invalid input. Please enter a number.\n");
                input.next();
                continue;
            }
            price = input.nextDouble();
            if (price < 0) {  // check the price is larger than 0
                System.out.println("Invalid price. Please try again.");
            }
        }

        // Create ticket and add to list
        Person person = new Person(name, surname, email);
        Ticket ticket = new Ticket(row, seat, price, person);
        tickets.add(ticket);

        // Update seats array
        seats[row][seat] = 1;

        // Print confirmation message and ticket details
        double totalPrice = ticket.getPrice();
        System.out.println("\nThank you! Ticket bought successfully");
        System.out.println("Row  : " + (row + 1));
        System.out.println("Seat : " + (seat + 1));
        System.out.println("Price: $" + price);
        System.out.println("\n-------------------------------------------------");

    }

    // method for print seating area
    private static void print_seating_area() {
        System.out.print("\n");
        System.out.println("     ***********");
        System.out.println("     *  STAGE  *");
        System.out.println("     ***********");

        // Print seat rows
        for (int i = 0; i < seats.length; i++) {
            // Calculate padding based on row length
            int padding = (ROW_LENGTHS[ROW_LENGTHS.length-1] - ROW_LENGTHS[i]) / 2;
            // Print left padding
            for (int p = 0; p < padding; p++) {
                System.out.print(" ");
            }

            for (int j = 0; j < ROW_LENGTHS[i]; j++) {
                System.out.print(seats[i][j] == 1 ? "X" : "O");   // Print X for booked seat and O for available seat

                if (j == ROW_LENGTHS[i] / 2 - 1) {    // Add space after middle seat
                    System.out.print(" ");
                }
            }
            // Print right padding
            for (int p = 0; p < padding; p++) {
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("\n-------------------------------------------------");
    }


    // method for cancel a ticket
    private static void cancel_ticket() {
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter row number (1-3)  : ");
        int row = input.nextInt();
        System.out.print("Enter seat number (1-20): ");
        int seat = input.nextInt();

        if (row < 1 || row > 3 || seat < 1 || seat > ROW_LENGTHS[row - 1]) {  // check the row and seat numbers are in correct range
            System.out.println("Invalid row or seat number.");
            return;
        }

        boolean foundTicket = false;
        for (int i = 0; i < tickets.size(); i++) {   // for loop iterates through all the tickets stored in the tickets list
            Ticket ticket = tickets.get(i);
            if (ticket.getRow() == row - 1 && ticket.getSeat() == seat - 1) {
                seats[row - 1][seat - 1] = 0;
                System.out.println("Ticket canceled.");
                System.out.println("-------------------------------------------------");
                double totalPrice = ticket.getPrice();
                tickets.remove(i);
                foundTicket = true;
                break;
            }
        }
        if (!foundTicket) {
            System.out.println("No ticket found at row " + row + ", seat " + seat + ".");
            System.out.println("-------------------------------------------------");
        }
    }

    // method for prints available seats
    private static void show_available() {
        System.out.println("\nAvailable seats:");
        for (int i = 0; i < seats.length; i++) {
            System.out.print("Row " + (i+1) + ": ");
            for (int j = 0; j < ROW_LENGTHS[i]; j++) {
                if (seats[i][j] == 0) {   //check the seat available or not
                    System.out.print((j+1) + " ");
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------");
    }



    private static final String FILENAME = "seats.txt"; // file name where the data to be saved

    // method to save data to seats.txt file
    private static void save() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILENAME));  // creates a new PrintWriter object that will be used to write data to a file,
                                                                            //  it uses a FileWriter object to specify the file to write to, which is stored in the FILENAME variable
            for (int i = 0; i < seats.length; i++) {   // iterate over each row in the seats array
                for (int j = 0; j < ROW_LENGTHS[i]; j++) {   // iterate over each seat in the current row
                    writer.print(seats[i][j] + " ");   // writes the current seat value to the file using the writer object
                }
                writer.println();
            }
            writer.close();
            System.out.println("\nData saved to file successfully.");
            System.out.println("-------------------------------------------------");
        } catch (IOException e) {
            System.out.println("\nError writing to file.");
            System.out.println("-------------------------------------------------");
        }
    }


    // method for load previously saved data to the file
    private static void load() {
        try {
            File file = new File(FILENAME);  // creates a new File object named "file" with the name of the file to be loaded, which is stored in a constant named "FILENAME".
            Scanner fileInput = new Scanner(file);

            // Read and update seats array from file
            for (int i = 0; i < seats.length; i++) {
                String line = fileInput.nextLine();  // reads the next line of input from the file and stores it in a String variable line
                String[] values = line.split(" ");   // splits the line String into an array of Strings named values using space characters as the delimiter
                for (int j = 0; j < values.length; j++) {
                    seats[i][j] = Integer.parseInt(values[j]);  // https://www.javatpoint.com/java-integer-parseint-method
                }
            }

            System.out.println("\nData loaded from file.");
            System.out.println("-------------------------------------------------");

        } catch (FileNotFoundException e) {
            System.out.println("\nFile not found.");
            System.out.println("-------------------------------------------------");
        } catch (Exception e) {
            System.out.println("\nError loading data from file: " + e.getMessage());
            System.out.println("-------------------------------------------------");
        }
    }


    // method to show the ticket information
    public static void show_tickets_info() {
        double totalPrice = 0;   // declare variables
        int ticketCount = 0;
        for (Ticket ticket : tickets) {   // for loop that iterates over an array of Ticket objects named tickets
            if (ticket != null) {
                ticketCount++;
                System.out.println("\nTicket " + ticketCount + ":");
                ticket.print();
                System.out.println("\t");
                totalPrice += ticket.getPrice();
            }
        }
        System.out.println("Total Price: $" + totalPrice);
        System.out.println("-------------------------------------------------");
    }

    // method for sort tickets price in ascending order
    public static List<Ticket> sort_tickets() {
        List<Ticket> sortedTickets = new ArrayList<>(tickets);   //creates a new list,sortedTickets that contains the same elements as the tickets and
                                                                 // The new ArrayList<>(tickets) creates a new ArrayList has the same elements as tickets
        Collections.sort(sortedTickets, new Comparator<Ticket>() {
            public int compare(Ticket t1, Ticket t2) {
                if (t1.getPrice() < t2.getPrice()) {    // comparator compares two Ticket objects(t1,t2) according their getPrice() method
                    return -1;
                } else if (t1.getPrice() > t2.getPrice()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        for (Ticket ticket : sortedTickets) {    // iterates over the sortedTickets list and prints each ticket using its print() method
            ticket.print();
            System.out.println("\n");

        }
        return sortedTickets;
    }

}
