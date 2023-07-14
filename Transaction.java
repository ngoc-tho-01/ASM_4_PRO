package vn.funix.fx20193.java.asm04.model;

import vn.funix.fx20193.java.asm04.common.Utils;

import java.io.Serializable;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;

    private TransactionType type;

    public Transaction() {

    }

    public Transaction(String accountNumber, double amount, String time, boolean status, TransactionType type) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = time;
        this.status = status;
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[GD]%8s | %9s | %17s | %19s", accountNumber, type, Utils.formatBalance(amount), time);
    }
}
