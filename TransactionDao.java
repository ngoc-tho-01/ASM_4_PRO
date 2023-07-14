package vn.funix.fx20193.java.asm04.dao;

import vn.funix.fx20193.java.asm04.binaryFile.BinaryFileService;
import vn.funix.fx20193.java.asm04.model.Transaction;

import java.io.Serializable;
import java.util.List;

public class TransactionDao implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String FILE_PATH = "store/transaction.dat";

    // Phương thức save dùng để lưu lịch sử giao dịch vào file.
    public static void save(List<Transaction> transactions) {
        BinaryFileService.writeFile(FILE_PATH, transactions);
    }

    // Phương thức list dùng để đọc lịch sử giao dịch từ file.
    public static List<Transaction> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }
}
