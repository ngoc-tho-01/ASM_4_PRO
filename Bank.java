package vn.funix.fx20193.java.asm04.model;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bank {
    private String id;

    public Bank() {
    }

    // Constructor Bank.
    public Bank(String id) {
        this.id = String.valueOf(UUID.randomUUID());
    }

    // Các phương thức getter setter để lấy dữ liệu và thay đổi dữ liệu.
    public String getId() {
        return id;
    }

    // Hàm sét điều kiện thoát vòng lặp để thêm CCCD cho khách hàng.
    public boolean validateCustomerId(String customerId) {
        if (customerId.length() != 12) {
            return false;
        }
        Pattern pattern = Pattern.compile("^0[0-9]{2}[0-3]{1}[0-9]{2}[0-9]{6}$");
        Matcher matcher = pattern.matcher(customerId);
        return matcher.matches();
    }
}
