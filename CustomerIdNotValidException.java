package vn.funix.fx20193.java.asm04.exception;

public class CustomerIdNotValidException extends Exception {
    private String err;

    public CustomerIdNotValidException(String err) {
        this.err = err;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
