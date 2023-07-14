package vn.funix.fx20193.java.asm04.binaryFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileService {
    // Phương thức đọc file đầu vào là đường dẫn đến thư mục, đầu ra là danh sách đối tượng.
    public static <T> List<T> readFile(String fileName) {
        List<T> objects = new ArrayList<>();
        // Khai báo lớp ObjectInputStream để đọc tất cả dữ liệu từ file vào.
        try (ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    // gọi hàm readObject để đọc đối tượng.
                    T object = (T) file.readObject();
                    objects.add(object);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Dữ liệu trong file không hợp lệ hoặc file không tồn tại.");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        }
        return objects;
    }

    // Phương thức ghi file.
    public static <T> void writeFile(String fileName, List<T> objects) {
        // Khai báo lớp ObjectOutputStream để ghi tất cả dữ liệu từ file vào.
        try (ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            for (T object : objects) {
                // Duyệt qua đối tượng để lấy từng phần tử để lưu vào file.
                file.writeObject(object);
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}
