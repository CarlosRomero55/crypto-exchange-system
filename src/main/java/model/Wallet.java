

package model;

import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private double fiatBalance;
    private Map<String, Double> cryptoHoldings;

    public Wallet() {
        this.fiatBalance = 0;
        this.cryptoHoldings = new HashMap<>();
    }

    public double getFiatBalance() { return fiatBalance; }
    public void depositFiat(double amount) { this.fiatBalance += amount; }

    public Map<String, Double> getCryptoHoldings() { return cryptoHoldings; }
    public void addCrypto(String ticker, double amount) {
        cryptoHoldings.put(ticker, cryptoHoldings.getOrDefault(ticker, 0.0) + amount);
    }

    public void subtractFiat(double amount) { this.fiatBalance -= amount; }
    public void subtractCrypto(String ticker, double amount) {
        cryptoHoldings.put(ticker, cryptoHoldings.get(ticker) - amount);
    }
}
