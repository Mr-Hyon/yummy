package yummy.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.BankAccountDao;
import yummy.dao.MenuDao;
import yummy.dao.OrderDao;
import yummy.dao.UserDao;
import yummy.model.BankAccount;
import yummy.model.Menu;
import yummy.model.MyOrder;
import yummy.model.User;
import yummy.service.BankAccountService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    BankAccountDao bankAccountDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Autowired
    MenuDao menuDao;

    public boolean BindBankAccount(BankAccount bankAccount){
        if(bankAccountDao.getBankAccountByUser(bankAccount.getUid()) == null &&
                bankAccountDao.getBankAccountByAccountId(bankAccount.getAccountid()) == null){
            bankAccountDao.addBankAccount(bankAccount);
            return true;
        }
        else{
            return false;
        }
    }

    public BankAccount getUserBankAccount(int uid){
        return bankAccountDao.getBankAccountByUser(uid);
    }

    public void payOrder(BankAccount bankAccount,MyOrder order){
        double alter_balance = bankAccount.getBalance() - order.getPrice();
        bankAccount.setBalance(alter_balance);
        order.setState("PAID");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setPayTime(sdf.format(date));
        bankAccountDao.modifyBankAccount(bankAccount);
        orderDao.modifyOrder(order);
    }

    public String refundOrder(BankAccount bankAccount, MyOrder myOrder){
        String ids[] = myOrder.getMenus_id().split("#");
        String nums[] = myOrder.getMenus_num().split("#");
        for(int i=0;i<ids.length;i++){
            Menu sample = menuDao.getMenuById(Integer.parseInt(ids[i]));
            int plus_num = sample.getNum() + Integer.parseInt(nums[i]);
            sample.setNum(plus_num);
            menuDao.modifyMenu(sample);
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long diff = null;
        try {
            Date payTime = sdf.parse(myOrder.getPayTime());
            diff = sdf.parse(sdf.format(date)).getTime() - sdf.parse(sdf.format(payTime)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(diff <= 1 * 60 * 1000){
            double alter_balance = bankAccount.getBalance() + myOrder.getPrice();
            bankAccount.setBalance(alter_balance);
            myOrder.setState("REFUND");
            myOrder.setEndTime(sdf.format(date));
            bankAccountDao.modifyBankAccount(bankAccount);
            orderDao.modifyOrder(myOrder);
            return "full";
        }
        else if(diff <= 10 * 60 * 1000){
            double alter_balance = bankAccount.getBalance() + myOrder.getPrice()/2;
            bankAccount.setBalance(alter_balance);
            myOrder.setState("REFUND");
            myOrder.setEndTime(sdf.format(date));
            bankAccountDao.modifyBankAccount(bankAccount);
            orderDao.modifyOrder(myOrder);
            return "half";
        }
        else{
            myOrder.setState("REFUND");
            myOrder.setEndTime(sdf.format(date));
            orderDao.modifyOrder(myOrder);
            return "zero";
        }
    }
}
