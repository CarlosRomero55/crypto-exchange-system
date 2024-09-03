package controller;



import model.Cryptocurrency;
import model.Transaction;
import model.User;
import service.ExchangeService;
import service.UserService;

public class ExchangeController {
    private UserService userService;
    private ExchangeService exchangeService;

    public ExchangeController() {
        this.userService = new UserService();
        this.exchangeService = new ExchangeService();
    }

    public User registerUser(String name, String email) {
        return userService.registerUser(name, email);
    }

    public boolean buyCrypto(User buyer, User seller, Cryptocurrency crypto, double amount) {
        return exchangeService.executeTransaction(buyer, seller, crypto, amount);
    }

    public boolean sellCrypto(User seller, User buyer, Cryptocurrency crypto, double amount) {
        return exchangeService.executeTransaction(buyer, seller, crypto, amount);
    }

    public UserService getUserService() {
        return userService;
    }

    public ExchangeService getExchangeService() {
        return exchangeService;
    }
}
