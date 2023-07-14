package vn.funix.fx20193.java.asm04.junit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.funix.fx20193.java.asm04.binaryFile.BinaryFileService;
import vn.funix.fx20193.java.asm04.model.Customer;

import java.util.ArrayList;
import java.util.List;

class BinaryFileServiceTest {
    List<Customer> customers = new ArrayList<>();
    Customer customer;
    String fileName = "D:\\Môn Pro\\PRO192x_ASM4_thodnFX20193@funix.edu.vn\\PRO192x_ASM4_thodnFX20193@funix.edu.vn\\Asm04\\src\\vn\\funix\\fx20193\\java\\asm04\\junit_test\\customersTest.dat";

    @BeforeEach
    void setUp() {
        customer = new Customer("051200004972", "Đặng Ngọc Thọ");
        customers.add(customer);
        customer = new Customer("051200000000", "Đặng Hoàng Long");
        customers.add(customer);
    }

    // Phương thức test đọc file.
    @Test
    void readFile() {
        System.out.println(BinaryFileService.readFile(fileName));
    }

    // Phương thức test lưu file.
    @Test
    void writeFile() {
        BinaryFileService.writeFile(fileName, customers);
    }
}