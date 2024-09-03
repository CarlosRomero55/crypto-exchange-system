

package model;

public class Transaction {
    private User buyer;
    private User seller;
    private Cryptocurrency cryptocurrency;
    private double amount;
    private double price;

    public Transaction(User buyer, User seller, Cryptocurrency cryptocurrency, double amount, double price) {
        this.buyer = buyer;
        this.seller = seller;
        this.cryptocurrency = cryptocurrency;
        this.amount = amount;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Transaction: %s bought %.2f %s from %s at $%.2f",
                buyer.getName(), amount, cryptocurrency.getTicker(), seller.getName(), price);
    }
}
