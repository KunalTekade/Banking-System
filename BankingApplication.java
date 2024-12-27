import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class BankingApplication {
    // A HashMap to store user data
    private static HashMap<String, User> userDatabase = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to the Banking System!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> loginUser();
                case 3 -> {
                    System.out.println("Thank you for using the Banking System!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to register a new user
    public static void registerUser() {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your address:");
        String address = scanner.nextLine();
        System.out.println("Enter your contact:");
        String contact = scanner.nextLine();
        System.out.println("Enter initial deposit:");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Create a password:");
        String password = scanner.nextLine();

        // Generate a unique account number
        String accountNumber = UUID.randomUUID().toString();

        // Create a new user and store in the database
        User user = new User(name, address, contact, balance, password, accountNumber);
        userDatabase.put(accountNumber, user);

        System.out.println("Registration successful! Your account number is: " + accountNumber);
    }

    // Method to log in an existing user
    public static void loginUser() {
        System.out.println("Enter your account number:");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        // Retrieve the user from the database
        User user = userDatabase.get(accountNumber);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful! Welcome, " + user.getName());
            afterLoginMenu(user);
        } else {
            System.out.println("Invalid account number or password.");
        }
    }

    // After login menu for logged-in users
    public static void afterLoginMenu(User user) {
        while (true) {
            System.out.println("\nHello, " + user.getName() + "!");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> System.out.println("Your current balance is: " + user.getBalance());
                case 2 -> {
                    System.out.println("Enter the amount to deposit:");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    user.deposit(amount);
                    System.out.println("Deposit successful! Your new balance is: " + user.getBalance());
                }
                case 3 -> {
                    System.out.println("Enter the amount to withdraw:");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    if (amount > user.getBalance()) {
                        System.out.println("Insufficient funds.");
                    } else {
                        user.withdraw(amount);
                        System.out.println("Withdrawal successful! Your new balance is: " + user.getBalance());
                    }
                }
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Inner User class to represent a bank user
    static class User {
        private String name;
        private String address;
        private String contact;
        private double balance;
        private String password;
        private String accountNumber;

        // Constructor for User class
        public User(String name, String address, String contact, double balance, String password, String accountNumber) {
            this.name = name;
            this.address = address;
            this.contact = contact;
            this.balance = balance;
            this.password = password;
            this.accountNumber = accountNumber;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public double getBalance() {
            return balance;
        }

        // Methods to deposit and withdraw money
        public void deposit(double amount) {
            balance += amount;
        }

        public void withdraw(double amount) {
            balance -= amount;
        }
    }
}
