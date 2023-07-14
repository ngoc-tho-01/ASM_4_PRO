package vn.funix.fx20193.java.asm04.model;

import vn.funix.fx20193.java.asm04.common.Utils;
import vn.funix.fx20193.java.asm04.dao.TransactionDao;

import java.util.List;
import java.io.Serializable;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String customerId;
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account(String customerId) {
        this.customerId = customerId;
    }

    public Account(String customerId, String accountNumber, double balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account() {

    }

    // Các phương thức getter setter để lấy dữ liệu và thay đổi dữ liệu.
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accounNumber) {
        this.accountNumber = accounNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    // Phương thức getTransactions lấy ra các giao dịch thuộc account này.
    public List<Transaction> getTransactions() {
        List<Transaction> transactions = TransactionDao.list();
        return transactions.stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber)).collect(Collectors.toList());
    }

    // Hàm displayTransaction dùng để hiển thị lịch sử giao dịch.
    public void displayTransactionList() {
        for (Transaction transaction : getTransactions()) {
            System.out.println(transaction);
        }
    }

    // Hàm thức cho biết tài khoản có phải là premium hay không.
    public boolean isPremium() {
        return balance >= 10000000;
    }

    // Phương thức input(Scanner scanner) để thêm tài khoản mới vào danh sách.
    public void input(Scanner sc) {
        // Thêm số tài khoản cho khách hàng, kiểm tra tài khoản k tồn tại thì thêm vào.
        while (true) {
            System.out.print("Nhập số tài khoản gồm 6 chữ số: ");
            this.accountNumber = sc.nextLine();
            if (this.accountNumber.length() == 6) {
                try {
                    Integer.parseInt(this.accountNumber);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Nhập số tài khoản phải là số.");
                }
            }
        }
        // Thêm số tiền ban đầu cho tài khoản.
        while (true) {
            try {
                System.out.print("Nhập số dư tài khoản >= 50000đ: ");
                this.balance = Double.parseDouble(sc.nextLine());
                if (this.balance >= 50000) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Nhập số tiền phải là số!!!");
            }
        }
    }

    // Phương thức createTransaction dùng để lưu lịch sử giao dịch.
    public void createTransaction(double amount, String time, boolean status, TransactionType type) {
        List<Transaction> transactions = TransactionDao.list();
        Transaction transaction = new Transaction(accountNumber, amount, time, status, type);
        transactions.add(transaction);
        TransactionDao.save(transactions);
    }

    @Override
    public String toString() {
        return String.format("%10s | %50s", accountNumber, Utils.formatBalance(balance));
    }
}
