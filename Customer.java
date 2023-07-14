package vn.funix.fx20193.java.asm04.model;

import vn.funix.fx20193.java.asm04.common.Utils;
import vn.funix.fx20193.java.asm04.dao.AccountDao;
import vn.funix.fx20193.java.asm04.dao.CustomerDao;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    public Customer(String customerId, String name) {
        super(name, customerId);
    }

    public Customer() {

    }

    public Customer(List<String> values) {
        this(values.get(0), values.get(1));
    }

    // Phương thức dùng để tính toán số dư của khách hàng là tổng số dư của tất cả tài khoản mà khách hàng có.
    public double getBalance(List<Account> accounts) {
        double balance = 0;
        for (Account account : accounts) {
            balance += account.getBalance();
        }
        return balance;
    }

    //  Phương thức cho biết khách hàng có phải là premium hay không
    public boolean isPremium(List<Account> accounts) {
        for (Account account : accounts) {
            if (account.isPremium()) {
                return true;
            }
        }
        return false;
    }

    // Phương thức getAccounts() lấy ra những account có customerId bằng customerId hiện tại.
    public List<Account> getAccounts(List<Account> accountList) {
        return accountList.stream().filter(account -> account.getCustomerId().equals(this.getCustomerId())).collect(Collectors.toList());
    }

    // Phương thức displayInformation dùng để hiển thị cho đối tượng Customer
    public void displayInformation() {
        // Xét kiểu dữ liệu để hàm isPremium hiển thị ra kểu String.
        boolean isPre = isPremium(getAccounts(AccountDao.list()));
        String preText = "";
        if (isPre) {
            preText = "Premium";
        } else {
            preText = "Normal";
        }
        // Xuất thông tin ra màn hình

        System.out.printf("%1s | %20s | %7s | %18s\n", getCustomerId(), getName(), preText, Utils.formatBalance(getBalance(getAccounts(AccountDao.list()))));
        int i = 0;
        for (Account account : getAccounts(AccountDao.list())) {
            // Dùng instanceof để kiểm tra loại tài khoản.
            String accountType = "";
            if (account instanceof SavingsAccount) {
                accountType = "SAVINGS";
            }
            i++;
            System.out.printf("%1s%11s | %20s | %28s\n", i, account.getAccountNumber(), accountType, Utils.formatBalance(account.getBalance()));
        }
    }

    // Hàm displayTransaction dùng để hiển thị lịch sử giao dịch.
    public void displayTransactionInformation() {
        for (Account account : getAccounts(AccountDao.list())) {
            account.displayTransactionList();
        }
    }

    // Hàm getAccountByAccountNumber dùng để kiểm tra tài khoản đã tồn tại hay chưa và trả về tài khoản tương ứng.
    public Account getAccountByAccountNumber(List<Account> accounts, String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    // Phương thức withdraw(Scanner scanner) yêu cầu nhập số tài khoản để xử lý rút tiền.
    public void withdraw(Scanner sc) {
        List<Account> accounts = getAccounts(AccountDao.list());
        // Dùng isEmpty kiểm tra tài khoản có tồn tại thì tiếp tục chương trình.
        if (!accounts.isEmpty()) {
            Account account;
            String accountNumber = "";
            double amount = 0;
            do {
                System.out.print("Nhập số tài khoản: ");
                accountNumber = sc.nextLine();
                account = getAccountByAccountNumber(accounts, accountNumber);
            } while (account == null);

            do {
                System.out.print("Nhập số tiền rút: ");
                try {
                    amount = Double.parseDouble(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Nhập số tiền phải là số.");
                }
            } while (amount <= 0);
            if (account instanceof SavingsAccount) {
                // Kiểm tra điều kiện rút tiền hợp lệ thì update lại tài khoản và lưu lại lịch sưr giao dịch.
                if (!((SavingsAccount) account).withdraw(amount)) {
                    System.out.println("Giao dịch không hợp lệ");
                } else {
                    boolean status = true;
                    AccountDao.update(account);
                    account.createTransaction(-amount, Utils.getDataTime(), status, TransactionType.WITHDRAW);
                }
            }
        } else {
            System.out.println("khách hàng không có tài khoản nào, thao tác không thành công.");
        }
    }

    public void transfers(Scanner sc) {
        List<Customer> customers = CustomerDao.list();
        List<Account> accounts = getAccounts(AccountDao.list());
        // Phương thức isEmpty kiểm tra khách hàng có tài khoản thì tiếp tục thực hiện giao dịch.
        if (!accounts.isEmpty()) {
            Account transferAccount;
            Customer receivingCustomer = null;
            Account recevingAccount = null;
            String transferAccountNumber = "";
            String receivingAccountNumber = "";
            double amount = 0;
            //  Nhập đúng số tài khoản hiện có của khách hàng sẽ thoát vòng lặp.
            do {
                System.out.print("Nhập số tài khoản chuyển tiền: ");
                transferAccountNumber = sc.nextLine();
                transferAccount = getAccountByAccountNumber(accounts, transferAccountNumber);
            } while (transferAccount == null);

            // Nhập số tài khoản người nhận có trong tất cả khách hàng và khách với số tài khoản chuyển sẽ được thoát vòng lặp.
            do {
                System.out.print("Nhập số tài khoản nhận tiền(exit để thoát): ");
                receivingAccountNumber = sc.nextLine();
                if (receivingAccountNumber.equals("exit")) {
                    System.exit(0);
                } else {
                    // Duyệt qua danh sách khách hàng để lấy tài khoản của tất cả khách hàng.
                    for (Customer customer : customers) {
                        List<Account> accountList = customer.getAccounts(AccountDao.list());
                        for (Account account1 : accountList) {
                            if (account1.getAccountNumber().equals(receivingAccountNumber) && !(transferAccountNumber.equals(receivingAccountNumber))) {
                                receivingCustomer = customer;
                                recevingAccount = account1;
                                System.out.println("Gửi tiền đến tài khoản: " + recevingAccount.getAccountNumber() + " | " + receivingCustomer.getName());
                                break;
                            }
                        }
                    }
                }
            } while (recevingAccount == null);
            do {
                System.out.print("Nhập số tiền chuyển: ");
                try {
                    amount = Double.parseDouble(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Nhập số tiền phải là số.");
                }
            } while (amount <= 0);
            // Gọi hàm deliveryConfirmation để xác nhận giao dịch.
            transactionConfirmation(sc, transferAccount, recevingAccount, amount);
        } else {
            System.out.println("khách hàng không có tài khoản nào, thao tác không thành công.");
        }
    }

    // Hàm xác nhận giao dịch chuyển tiền.
    public void transactionConfirmation(Scanner sc, Account transferAccount, Account recevingAccount, double amount) {
        String confirm = "";
        while (true) {
            System.out.print("Xác nhận thực hiện chuyển " + Utils.formatBalance(amount) + " từ tài khoản [" + transferAccount.getAccountNumber() + "] đến tài khoản [" + recevingAccount.getAccountNumber() + "] (Y/N): ");
            confirm = sc.nextLine();
            // Nhập Y để xác nhận hoặc N để thoát.
            if (confirm.equals("Y")) {
                if (transferAccount instanceof SavingsAccount) {
                    // Kiểm tra thỏa mãn điều kiện giao dịch sẽ update lại số tiền và lưu lại lịch sử giao dịch.
                    if (((SavingsAccount) transferAccount).transfer(recevingAccount, amount)) {
                        boolean status = true;
                        // Update lại tài khoản chuyển.
                        AccountDao.update(transferAccount);
                        transferAccount.createTransaction(-amount, Utils.getDataTime(), status, TransactionType.TRANSFERS);
                        // Update lại tài khoản nhận.
                        AccountDao.update(recevingAccount);
                        recevingAccount.createTransaction(amount, Utils.getDataTime(), status, TransactionType.TRANSFERS);
                    }
                }
                break;
            } else if (confirm.equals("N")) {
                System.exit(0);
            } else {
                System.out.println("Xác nhận sai vui lòng nhập (Y) để xác nhận chuyển hoặc (N) để thoát.");
            }
        }
    }
}
