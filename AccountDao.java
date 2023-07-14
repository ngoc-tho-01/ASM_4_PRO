package vn.funix.fx20193.java.asm04.dao;

import vn.funix.fx20193.java.asm04.binaryFile.BinaryFileService;
import vn.funix.fx20193.java.asm04.model.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AccountDao implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "store/account.dat";

    // Phương thức save dùng để lưu tài khoản vào file.
    public static void save(List<Account> accounts) {
        BinaryFileService.writeFile(FILE_PATH, accounts);
    }

    // Phương thức list dùng để lấy tài khoản từ file.
    public static List<Account> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }

    // Phương thức AccountDao.update(account) để cập nhật số dư cho tài khoản.
    public static void update(Account editAccount) {
        List<Account> accounts = list();
        // Kiểm tra tài khoản có tồn tại hay chưa.
        boolean hasExit = accounts.stream().anyMatch(account -> account.getAccountNumber().equals(editAccount.getAccountNumber()));
        // Khai báo list để lưu tài khoản sau khi update.
        List<Account> updateAccount;
        // kiểm tra nếu tài khoản chưa tồn tại sẽ thêm tài khoản mới.
        if (!hasExit) {
            updateAccount = new ArrayList<>(accounts);
            updateAccount.add(editAccount);
        } else {
            // Tài khoản đã tồn tại sẽ kiểm tra và update lại.
            updateAccount = new ArrayList<>();
            List<Future<Account>> accountFutures = new ArrayList<>();
            // Tạo thread pool tạo luồn để xử lý.
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            for (Account account : accounts) {
                // Future trả về kết quả kiểm tra của Callable.
                Future<Account> accountFuture = executorService.submit(new MultiThreadCallable(account, editAccount));
                accountFutures.add(accountFuture);
            }
            // Duyệt qua List<Future<Account>> để lấy ra từng tài khoản và update lại.
            for (Future<Account> accountFuture : accountFutures) {
                try {
                    Account account = accountFuture.get();
                    updateAccount.add(account);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            executorService.shutdown();
        }
        save(updateAccount);
    }
}
