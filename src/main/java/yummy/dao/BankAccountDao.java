package yummy.dao;

import yummy.model.BankAccount;

public interface BankAccountDao {

    public void addBankAccount(BankAccount bankAccount);

    public void modifyBankAccount(BankAccount bankAccount);

    public void deleteBankAccount(BankAccount bankAccount);

    public BankAccount getBankAccountById(int id);

    public BankAccount getBankAccountByUser(int uid);

    public BankAccount getBankAccountByAccountId(String accountId);
}
