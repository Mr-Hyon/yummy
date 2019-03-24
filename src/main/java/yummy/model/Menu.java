package yummy.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="menu")
public class Menu implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String image;
    private String startDate;
    private String endDate;
    private int num;
    private double price;
    private String rid;
    private String desp;
    private String type;

    public Menu(){
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
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Column
    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        this.image = image;
    }

    @Column(nullable = false)
    public String getStartDate(){
        return this.startDate;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    @Column(nullable = false)
    public String getEndDate(){
        return this.endDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    @Column(nullable = false)
    public int getNum(){
        return this.num;
    }

    public void setNum(int num){
        this.num = num;
    }

    @Column(nullable = false)
    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    @Column(nullable = false)
    public String getRid(){
        return this.rid;
    }

    public void setRid(String rid){ this.rid = rid; }

    @Column(nullable = false)
    public String getDesp(){
        return this.desp;
    }

    public void setDesp(String desp){ this.desp = desp; }

    @Column(nullable = false)
    public String getType(){
        return this.type;
    }

    public void setType(String type){ this.type = type; }

}
