

package model;

import java.util.UUID;

public class User {
    private final String id;
    private String name;
    private String email;
    private Wallet wallet;

    public User(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.wallet = new Wallet();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Wallet getWallet() { return wallet; }
}
