package yummy.service;

import yummy.model.BankAccount;
import yummy.model.MyOrder;

public interface BankAccountService {

    public boolean BindBankAccount(BankAccount bankAccount);

    public BankAccount getUserBankAccount(int uid);

    public void payOrder(BankAccount bankAccount,MyOrder order);

    public String refundOrder(BankAccount bankAccount, MyOrder myOrder);

}
