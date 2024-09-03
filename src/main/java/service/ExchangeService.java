package service;


import model.Cryptocurrency;
import model.Transaction;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ExchangeService {
    private List<Transaction> transactions;

    public ExchangeService() {
        this.transactions = new ArrayList<>();
    }

    public boolean executeTransaction(User buyer, User seller, Cryptocurrency crypto, double amount) {
        double cost = crypto.getCurrentPrice() * amount;

        if (buyer.getWallet().getFiatBalance() >= cost && seller.getWallet().getCryptoHoldings().getOrDefault(crypto.getTicker(), 0.0) >= amount) {
            buyer.getWallet().subtractFiat(cost);
            seller.getWallet().subtractCrypto(crypto.getTicker(), amount);
            buyer.getWallet().addCrypto(crypto.getTicker(), amount);
            seller.getWallet().depositFiat(cost);

            Transaction transaction = new Transaction(buyer, seller, crypto, amount, cost);
            transactions.add(transaction);
            return true;
        }

        return false;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}


