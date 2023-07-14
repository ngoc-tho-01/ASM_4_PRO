package vn.funix.fx20193.java.asm04.junit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.funix.fx20193.java.asm04.dao.AccountDao;
import vn.funix.fx20193.java.asm04.model.Account;
import vn.funix.fx20193.java.asm04.model.Customer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    List<Account> accounts = new ArrayList<>();
    Customer customer = new Customer("051200004972", "Đặng Ngọc Thọ");
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account("051200004972", "010101", 5000000);
        accounts.add(account);
        account = new Account("051200004973", "090909", 10000000);
        accounts.add(account);
    }

    // Phương thức test tính tổng số tiền các tài khoản của khách hàng.
    @Test
    void getBalance() {
        assertEquals(customer.getBalance(accounts), 15000000);
    }

    // Phương thức test khách hàng có phải Premium hay Normal
    @Test
    void isPremium() {
        assertTrue(customer.isPremium(accounts));
    }

    // Phương thức test lấy ra tài khoản theo khách hàng.
    @Test
    void getAccounts() {
        System.out.println(customer.getAccounts(accounts));
    }

    // Phương thức test tài khoản đã tồn tại hay chưa và trả về tài khoản tương ứng.
    @Test
    void getAccountByAccountNumber() {
        String accountNumber = "010101";
        assertNotNull(customer.getAccountByAccountNumber(accounts, accountNumber));
    }
}