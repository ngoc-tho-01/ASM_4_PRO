package vn.funix.fx20193.java.asm04.testFileService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestFileService {

    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readFile(String fileName) {
        // List<List<String>> lưu nhiều khách hàng.
        List<List<String>> lists = new ArrayList<>();
        // khai báo BufferedReader để đọc văn bản từ tiệp.
        try (BufferedReader brd = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while (true) {
                // Đọc file
                line = brd.readLine();
                // File không tồn tại sẽ báo lỗi và thoát chương trình.
                if (line == null) {
                    break;
                }
                // Dùng dấu phẩy để tách giá trị trong file.
                String[] txt = line.split(COMMA_DELIMITER);
                String customerId = txt[0];
                String name = txt[1];
                List<String> list = new ArrayList<>();
                list.add(customerId);
                list.add(name);

                lists.add(list);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tệp không tồn tại");
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi đọc file");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Thông tin trong file không hợp lệ.");
        }
        return lists;
    }
}