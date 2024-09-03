

package model;

public abstract class Cryptocurrency {
    private String name;
    private String ticker;
    protected double currentPrice;

    public Cryptocurrency(String name, String ticker, double initialPrice) {
        this.name = name;
        this.ticker = ticker;
        this.currentPrice = initialPrice;
    }

    public String getName() { return name; }
    public String getTicker() { return ticker; }
    public double getCurrentPrice() { return currentPrice; }
}
