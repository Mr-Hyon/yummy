package yummy.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="restaurant")
public class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String rid;
    private String rname;
    private String password;
    private String type;
    private String location;
    private String menu;
    private boolean isPassed;

    public Restaurant(){
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

    @Column
    public String getRid(){
        return this.rid;
    }

    public void setRid(String rid){
        this.rid = rid;
    }

    @Column(nullable = false)
    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Column(nullable = false)
    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    @Column(nullable = false)
    public String getLocation(){
        return this.location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    @Column(nullable = false)
    public String getMenu(){
        return this.menu;
    }

    public void setMenu(String menu){
        this.menu = menu;
    }

    @Column(nullable = false)
    public String getRname(){
        return this.rname;
    }

    public void setRname(String rname){
        this.rname = rname;
    }

    @Column(nullable = false)
    public boolean getisPassed(){
        return this.isPassed;
    }

    public void setisPassed(boolean isPassed){
        this.isPassed = isPassed;
    }

}
