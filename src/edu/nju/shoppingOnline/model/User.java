package edu.nju.shoppingOnline.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    private String account;
    private String passwd;
    private String email;
    private Double balance;

    @Id
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
