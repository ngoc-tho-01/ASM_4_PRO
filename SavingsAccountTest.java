package vn.funix.fx20193.java.asm04.junit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.funix.fx20193.java.asm04.model.Account;
import vn.funix.fx20193.java.asm04.model.SavingsAccount;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SavingsAccountTest{

    @BeforeEach
    void setUp() {
    }

    // Phương thức test cho phương thức withdraw.
    @Test
    void withdraw() {
        SavingsAccount account = new SavingsAccount("123456",10000000);
        double amount = 5000000;
        boolean withdraw = account.withdraw(amount);
        assertTrue(withdraw);
    }

    // Phương thức test cho phương thức isAccepted.
    @Test
    void isAccepted() {
        SavingsAccount account = new SavingsAccount("123456",10000000);
        double amount = 5000000;
        boolean isAccepted = account.isAccepted(amount);
        assertTrue(isAccepted);
    }

    // Phương thức test cho phương thức transfer.
    @Test
    void transfer() {
        SavingsAccount account = new SavingsAccount("123456",10000000);
        Account receiveAccount = new Account("010101", 500000);
        double amount = 100000;
        boolean transfer = account.transfer(receiveAccount, amount);
        assertTrue(transfer);

    }
}