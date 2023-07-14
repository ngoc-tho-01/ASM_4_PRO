package vn.funix.fx20193.java.asm04.dao;

import vn.funix.fx20193.java.asm04.binaryFile.BinaryFileService;
import vn.funix.fx20193.java.asm04.model.Customer;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class CustomerDao implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "store/customers.dat";

    // Phương thức save dùng để lưu danh sách khách hàng vào file.
    public static void save(List<Customer> customers) {
        BinaryFileService.writeFile(FILE_PATH, customers);
    }

    // Phương thức list dùng để lấy khách hàng từ file.
    public static List<Customer> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }
}
