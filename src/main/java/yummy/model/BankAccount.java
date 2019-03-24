package yummy.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="bankaccount")
public class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int uid;
    private String accountid;
    private String password;
    private double balance;

    public BankAccount(){
        super();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Column(nullable = false)
    public int getUid(){
        return this.uid;
    }

    public void setUid(int uid){
        this.uid = uid;
    }

    @Column(nullable = false)
    public String getAccountid(){
        return this.accountid;
    }

    public void setAccountid(String accountid){
        this.accountid = accountid;
    }

    @Column(nullable = false)
    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Column(nullable = false)
    public double getBalance(){
        return this.balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }
}
