package vn.funix.fx20193.java.asm04.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utils {
    public static String formatBalance(double balance) {
        Locale locale = new Locale("vi", "VN");
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setGroupingSeparator(',');
        formatSymbols.setCurrencySymbol("đ");
        decimalFormat.setDecimalFormatSymbols(formatSymbols);
        return decimalFormat.format(balance);
    }

    public static String getDataTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return myFormatObj.format(myDateObj);
    }

    public static void line() {
        System.out.println("+----------+-------------------------------------------+----------+");
    }
}