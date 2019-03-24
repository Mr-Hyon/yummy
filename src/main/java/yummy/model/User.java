package yummy.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String uname;
    private String contact;
    private String password;
    private String address;
    private boolean isValid;
    private int credit;

    public User(){
        super();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id=id;
    }

    @Column(nullable = false)
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    @Column(nullable = false)
    public String getUname(){
        return this.uname;
    }

    public void setUname(String uname){
        this.uname = uname;
    }

    @Column(nullable = false)
    public String getContact(){
        return this.contact;
    }

    public void setContact(String contact){
        this.contact = contact;
    }

    @Column(nullable = false)
    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Column(nullable = false)
    public String getAddress(){ return this.address; }

    public void setAddress(String address){
        this.address = address;
    }

    @Column(nullable = false)
    public boolean getIsValid() { return this.isValid; }

    public void setIsValid(boolean isValid){
        this.isValid = isValid;
    }

    @Column(nullable = false)
    public int getCredit() { return this.credit; }

    public void setCredit(int credit){
        this.credit = credit;
    }
}
