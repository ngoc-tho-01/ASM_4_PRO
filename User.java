package vn.funix.fx20193.java.asm04.model;

import vn.funix.fx20193.java.asm04.common.Utils;
import vn.funix.fx20193.java.asm04.dao.AccountDao;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String customerId;

    public User() {

    }

    public User(String name, String customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    // Các phương thức getter setter để lấy dữ liệu và thay đổi dữ liệu.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return String.format("%s | %20s", customerId, name);
    }
}
