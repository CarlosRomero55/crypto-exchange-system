package app;
import controller.ExchangeController;
import model.Bitcoin;
import model.Ethereum;
import model.Cryptocurrency;
import model.User;

import java.util.Scanner;

public class CryptoExchangeSystemApp {

    private static Scanner scanner = new Scanner(System.in);
    private static ExchangeController exchangeController = new ExchangeController();

    public static void main(String[] args) {
        boolean running = true;
        User currentUser = null;

        while (running) {
            System.out.println("=== Crypto Exchange System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Wallet Balance");
            System.out.println("4. Deposit Money");
            System.out.println("5. Buy Cryptocurrency");
            System.out.println("6. Sell Cryptocurrency");
            System.out.println("7. View Transaction History");
            System.out.println("8. Quit");

            System.out.print("Enter your choice: ");
            int choice = getValidIntegerInput();

            switch (choice) {
                case 1:
                    currentUser = register();
                    break;
                case 2:
                    currentUser = login();
                    break;
                case 3:
                    if (isUserLoggedIn(currentUser)) {
                        viewWalletBalance(currentUser);
                    }
                    break;
                case 4:
                    if (isUserLoggedIn(currentUser)) {
                        depositMoney(currentUser);
                    }
                    break;
                case 5:
                    if (isUserLoggedIn(currentUser)) {
                        buyCrypto(currentUser);
                    }
                    break;
                case 6:
                    if (isUserLoggedIn(currentUser)) {
                        sellCrypto(currentUser);
                    }
                    break;
                case 7:
                    if (isUserLoggedIn(currentUser)) {
                        viewTransactionHistory();
                    }
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using the Crypto Exchange System!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static User register() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        User user = exchangeController.registerUser(name, email);
        System.out.println("User registered successfully! Your ID is: " + user.getId());
        return user;
    }

    private static User login() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        User user = exchangeController.getUserService().loginUser(email);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName());
        } else {
            System.out.println("Login failed! Invalid email.");
        }
        return user;
    }

    private static void viewWalletBalance(User user) {
        System.out.println("Fiat Balance: $" + user.getWallet().getFiatBalance());
        System.out.println("Cryptocurrency Holdings: " + user.getWallet().getCryptoHoldings());
    }

    private static void depositMoney(User user) {
        System.out.print("Enter the amount to deposit: ");
        double amount = getValidDoubleInput();
        user.getWallet().depositFiat(amount);
        System.out.println("Deposited $" + amount + " successfully! New balance: $" + user.getWallet().getFiatBalance());
    }

    private static void buyCrypto(User buyer) {
        System.out.println("Available cryptocurrencies: BTC (Bitcoin), ETH (Ethereum)");
        System.out.print("Enter the cryptocurrency you want to buy (BTC/ETH): ");
        String ticker = scanner.nextLine().toUpperCase();

        Cryptocurrency crypto = getCryptocurrencyByTicker(ticker);

        if (crypto != null) {
            System.out.print("Enter the amount to buy: ");
            double amount = getValidDoubleInput();

            double totalPrice = crypto.getCurrentPrice() * amount;
            System.out.println("The total price is $" + totalPrice);

            User seller = getDummySeller(crypto); // Un vendedor ficticio con suficiente balance

            if (exchangeController.buyCrypto(buyer, seller, crypto, amount)) {
                System.out.println("Successfully purchased " + amount + " " + ticker);
            } else {
                System.out.println("Transaction failed! Insufficient funds or seller lacks cryptocurrency.");
            }
        } else {
            System.out.println("Invalid cryptocurrency ticker. Please try again.");
        }
    }

    private static void sellCrypto(User seller) {
        System.out.println("Available cryptocurrencies: BTC (Bitcoin), ETH (Ethereum)");
        System.out.print("Enter the cryptocurrency you want to sell (BTC/ETH): ");
        String ticker = scanner.nextLine().toUpperCase();

        Cryptocurrency crypto = getCryptocurrencyByTicker(ticker);

        if (crypto != null) {
            System.out.print("Enter the amount to sell: ");
            double amount = getValidDoubleInput();

            User buyer = getDummyBuyer(); // Un comprador ficticio con suficiente balance

            if (exchangeController.sellCrypto(seller, buyer, crypto, amount)) {
                System.out.println("Successfully sold " + amount + " " + ticker);
            } else {
                System.out.println("Transaction failed! Insufficient cryptocurrency to sell.");
            }
        } else {
            System.out.println("Invalid cryptocurrency ticker. Please try again.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("Transaction History:");
        exchangeController.getExchangeService().getTransactions().forEach(System.out::println);
    }

    private static Cryptocurrency getCryptocurrencyByTicker(String ticker) {
        switch (ticker) {
            case "BTC":
                return new Bitcoin(50000); // Simulación de precio actual
            case "ETH":
                return new Ethereum(3000); // Simulación de precio actual
            default:
                return null;
        }
    }

    private static boolean isUserLoggedIn(User user) {
        if (user == null) {
            System.out.println("You must be logged in to perform this action.");
            return false;
        }
        return true;
    }

    // Validar la entrada del usuario asegurándose de que sea un entero
    private static int getValidIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // Validar la entrada del usuario asegurándose de que sea un número de tipo double
    private static double getValidDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    // Vendedor ficticio para simular una venta
    private static User getDummySeller(Cryptocurrency crypto) {
        User dummySeller = new User("DummySeller", "dummy@crypto.com");
        dummySeller.getWallet().addCrypto(crypto.getTicker(), 100); // Simula que el vendedor tiene 100 BTC/ETH
        return dummySeller;
    }

    // Comprador ficticio para simular una compra
    private static User getDummyBuyer() {
        User dummyBuyer = new User("DummyBuyer", "dummybuyer@crypto.com");
        dummyBuyer.getWallet().depositFiat(100000); // Simula que el comprador tiene $100,000
        return dummyBuyer;
    }
}
