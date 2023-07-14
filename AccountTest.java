package vn.funix.fx20193.java.asm04.junit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.funix.fx20193.java.asm04.model.Account;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountNumber("123456");
        account.setBalance(10000000);
    }

    // Phương thức kiểm tra tài khoản có phải Premium hay không
    @Test
    void isPremium() {
        assertTrue(account.isPremium());
    }
}