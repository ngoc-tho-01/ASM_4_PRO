package vn.funix.fx20193.java.asm04.service;

import vn.funix.fx20193.java.asm04.model.Account;

public interface ITransfer {
    boolean transfer(Account receiveAccount, double amount);
}
