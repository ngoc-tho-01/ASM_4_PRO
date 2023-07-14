package vn.funix.fx20193.java.asm04.junit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.funix.fx20193.java.asm04.exception.CustomerIdNotValidException;
import vn.funix.fx20193.java.asm04.model.Account;
import vn.funix.fx20193.java.asm04.model.Customer;
import vn.funix.fx20193.java.asm04.model.DigitalBank;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DigitalBankTest {
    List<Customer> customers = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    public DigitalBank bank = new DigitalBank();

    @BeforeEach
    void setUp() {
        Customer customer = new Customer("051200004972", "Đặng Ngọc Thọ");
        customers.add(customer);
        Account account = new Account("010101", 500000);
        accounts.add(account);
    }

    // Phương thức test tài khoản có tồn tại hay chưa.
    @Test
    void isAccountExisted() {
        Account account = new Account("010101", 500000);
        assertTrue(bank.isAccountExisted(accounts, account));
    }

    // Phương thức test tài khách hàng có tồn tại hay chưa.
    @Test
    void isCustomerExisted() {
        Customer customer = new Customer("051200004972", "Đặng Ngọc Thọ");
        assertTrue(bank.isCustomerExisted(customers, customer));
    }

    // Phương thức test lấy ra một customer có id bằng id cho trước.
    @Test
    void getCustomerById() throws CustomerIdNotValidException {
        String customerId = "051200004972";
        assertNotNull(bank.getCustomerById(customers, customerId));
    }
}