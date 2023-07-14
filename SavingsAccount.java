package vn.funix.fx20193.java.asm04.model;

import vn.funix.fx20193.java.asm04.common.Utils;
import vn.funix.fx20193.java.asm04.service.ITransfer;
import vn.funix.fx20193.java.asm04.service.ReportService;
import vn.funix.fx20193.java.asm04.service.Withdraw;

import java.io.Serializable;

public class SavingsAccount extends Account implements ReportService, Withdraw, ITransfer, Serializable {
    private static final long serialVersionUID = 1L;

    final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;

    public SavingsAccount() {
    }

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public SavingsAccount(String customerId) {
        super(customerId);
    }

    // Hàm log để in ra biên lai savings mỗi khi rút tiền thành công.
    @Override
    public void log(double amount) {
        Utils.line();
        System.out.println("        BIÊN LAI GIAO DỊCH SAVINGS");
        System.out.printf("Ngày G/D: %38s\n", Utils.getDataTime());
        System.out.printf("ATM ID: %40s\n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SỐ TK: %41s\n", getAccountNumber());
        System.out.printf("SỐ TIỀN: %39s\n", Utils.formatBalance(amount));
        System.out.printf("SỐ DƯ: %41s\n", Utils.formatBalance(getBalance()));
        System.out.printf("PHÍ + VAT: %37s\n", Utils.formatBalance(0));
        Utils.line();
    }

    // Hàm withdraw dùng để cho phép khách hàng rút tiền.
    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            // Khai báo biến newBalance để tính lại số tiền sau khi rút và gán lại giá trị.
            double newBalance = getBalance() - amount;
            setBalance(newBalance);
            System.out.println("Rút tiền thành công, biên lai giao dịch:");

            log(amount);
            return true;
        }
        return false;
    }

    // Hàm implements interface Withdraw kiểm tra xem giá trị có thoả điều kiện rút tiền hay không.
    @Override
    public boolean isAccepted(double amount) {
        boolean isAccept = true;
        // Số tiền rút phải lớn hơn hoặc bằng 50.000đ
        if (amount < 50000) {
            isAccept = false;
        }
        // Số tiền 1 lần rút không được quá 5.000.000đ đối với tài khoản thường, và không giới hạn đối với tài khoản Premium.
        if (amount > SAVINGS_ACCOUNT_MAX_WITHDRAW && !isPremium()) {
            isAccept = false;
        }
        // Số dư còn lại sau khi rút phải lớn hơn hoặc bằng 50.000đ
        if (getBalance() - amount < 50000) {
            isAccept = false;
        }
        // Số tiền rút phải là bội số của 10.000đ
        if (amount % 10000 != 0) {
            isAccept = false;
        }
        return isAccept;
    }

    // Phương thức kiểm tra điều kiện chuyển tiền, nếu hợp lệ thì tạo một giao dịch trừ tiền của người gửi
    // và giao dịch cộng tiền cho người nhận, nếu không hợp lệ thì trả về lỗi.
    @Override
    public boolean transfer(Account receiveAccount, double amount) {
        if (isAccepted(amount)) {
            double transferNewBalance = this.getBalance() - amount;
            setBalance(transferNewBalance);
            double receivingNewBalance = receiveAccount.getBalance() + amount;
            receiveAccount.setBalance(receivingNewBalance);
            System.out.println("Chuyển tiền thành công, biên lai giao dịch:");
            log(receiveAccount, amount);
            return true;
        } else {
            System.out.println("Giao dịch không hợp lệ");
            return false;
        }
    }

    // Nạp chồng lại phương thức log() để thêm tài khoản nhận tiền vào biên lai.
    public void log(Account receiveAccount, double amount) {
        Utils.line();
        System.out.println("        BIÊN LAI GIAO DỊCH SAVINGS");
        System.out.printf("Ngày G/D: %38s\n", Utils.getDataTime());
        System.out.printf("ATM ID: %40s\n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SỐ TK: %41s\n", getAccountNumber());
        System.out.printf("SỐ TK NHẬN: %36s\n", receiveAccount.getAccountNumber());
        System.out.printf("SỐ TIỀN CHUYỂN: %32s\n", Utils.formatBalance(amount));
        System.out.printf("SỐ DƯ TK: %38s\n", Utils.formatBalance(getBalance()));
        System.out.printf("PHÍ + VAT: %37s\n", Utils.formatBalance(0));
        Utils.line();
    }
}
