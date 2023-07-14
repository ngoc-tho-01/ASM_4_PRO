package vn.funix.fx20193.java.asm04.dao;

import vn.funix.fx20193.java.asm04.model.Account;

import java.util.concurrent.Callable;

// Tạo class MultiThreadCallable kết thừa lại interface Callable để kiểm tra và trả về kết quả so sánh tài khoản.
public class MultiThreadCallable implements Callable<Account> {
    private final Account dbAccount;
    private final Account editAccount;

    public MultiThreadCallable(Account dbAccount, Account editAccount) {
        this.dbAccount = dbAccount;
        this.editAccount = editAccount;
    }

    @Override
    public Account call() throws Exception {
        if (dbAccount.getAccountNumber().equals(editAccount.getAccountNumber())) {
            return editAccount;
        }
        return dbAccount;
    }
}
