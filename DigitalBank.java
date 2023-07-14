package vn.funix.fx20193.java.asm04.model;

import vn.funix.fx20193.java.asm04.common.Utils;
import vn.funix.fx20193.java.asm04.dao.AccountDao;
import vn.funix.fx20193.java.asm04.dao.CustomerDao;
import vn.funix.fx20193.java.asm04.exception.CustomerIdNotValidException;
import vn.funix.fx20193.java.asm04.testFileService.TestFileService;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class DigitalBank extends Bank implements Serializable {

    private static final long serialVersionUID = 1L;


    public DigitalBank() {
        super();
    }

    // Hàm displayTransaction dùng để hiển thị lịch sử giao dịch(Chức năng 5).
    public void displayTransaction() {
        for (Customer customer : CustomerDao.list()) {
            customer.displayTransactionInformation();
        }
    }

    // Phương thức sẽ đọc dữ liệu từ file sử dụng hàm CustomerDao.list() để lấy danh sách khách hàng
    // và hiển thị thông qua hàm displayInformation.
    public void showCustomer() {
        List<Customer> customers = CustomerDao.list();
        if (customers.isEmpty()) {
            System.out.println("Chưa có khách hàng nào trong danh sách!");
        } else {
            for (Customer cus : customers) {
                cus.displayInformation();
            }
        }
    }

    // Phương thức sẽ đọc dữ liệu từ file truyền vào.
    public void addCustomer(String fileName) {
        List<List<String>> lists = TestFileService.readFile(fileName);
        List<Customer> customers = CustomerDao.list();
        boolean validData = false;
        int count = 0;
        for (List<String> item : lists) {
            Customer customer = new Customer(item);
            // item chứa 2 phần tử là customerId và name
            if (isCustomerExisted(customers, customer)) {
                System.out.println("Khách hàng " + item.get(0) + " đã tồn tại, thêm khách hàng không thành công.");
            } else if (!validateCustomerId(item.get(0))) {
                System.out.println("Không thể thêm khách hàng: " + item.get(0) + " số ID không hợp lệ.");
            } else {
                validData = true;
                customers.add(customer);
                System.out.println("Đã thêm khách hàng " + item.get(0) + ", vào danh sách khách hàng.");
                count++;
            }
        }
        if (validData) {
            // Gọi hàm save để lưu vào file customers.dat.
            CustomerDao.save(customers);
        }
    }

    // Phương thức addSavingsAccount để tạo mới một tài khoản ATM cho một khách hàng và lưu tài khoản vào file.
    public void addSavingsAccount(Scanner sc, String customerId) throws CustomerIdNotValidException {
        List<Account> accounts = AccountDao.list();
        if (getCustomerById(CustomerDao.list(), customerId) != null) {
            Account account = new SavingsAccount(customerId);
            account.input(sc);
            // kiểm tra tài khoản chưa tồn tại thì thêm tài khoản cho khách hàng và lưu tài khoản vào file.
            if (!(isAccountExisted(accounts, account))) {
                boolean status = true;
                AccountDao.update(account);
                account.createTransaction(account.getBalance(), Utils.getDataTime(), status, TransactionType.DEPOSIT);
                System.out.println("Tạo tài khoản thành công.");
            } else {
                System.out.println("Tài khoản đã tồn tại, tạo tài khoản không thành công.");
            }
        }
    }

    // Hàm withdraw dùng để cho phép khách hàng rút tiền theo tài khoản.
    public void withdraw(Scanner sc, String customerId) throws CustomerIdNotValidException {
        Customer customer = getCustomerById(CustomerDao.list(), customerId);
        customer.displayInformation();
        customer.withdraw(sc);
    }

    public void transfers(Scanner sc, String customerId) throws CustomerIdNotValidException {
        Customer customer = getCustomerById(CustomerDao.list(), customerId);
        customer.displayInformation();
        customer.transfers(sc);
    }

    // Phương thức kiểm tra một account có tồn tại trong mảng hay không.
    public boolean isAccountExisted(List<Account> accountsList, Account newAccount) {
        return accountsList.stream().anyMatch(account -> account.getAccountNumber().equals(newAccount.getAccountNumber()));
    }

    // Phương thức kiểm tra một customer có tồn tại trong mảng hay không.
    public boolean isCustomerExisted(List<Customer> customers, Customer newCustomer) {
        return customers.stream().anyMatch(customer -> customer.getCustomerId().equals(newCustomer.getCustomerId()));
    }

    // Phương thức lấy ra một customer có id bằng id cho trước.
    public Customer getCustomerById(List<Customer> customerList, String customerId) throws CustomerIdNotValidException {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        throw new CustomerIdNotValidException("Không tìm thấy khách hàng " + customerId + ", tác vụ không thành công.");
    }
}
