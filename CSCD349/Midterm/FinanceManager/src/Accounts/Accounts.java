package Accounts;

import java.util.Observable;

public class Accounts extends Observable {
    private double balance;
    private String AccountName;

    Accounts(String n, double b){
        this.AccountName = n;
        this.balance = b;
    }
    public String getAccountName(){
        return this.AccountName;
    }
    public double getBalance(){
        return this.balance;
    }
    public void setBalance(double b){
        this.balance = b;
        setChanged();
        notifyObservers(this.balance);
        clearChanged();
    }
}
