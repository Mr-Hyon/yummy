package yummy.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="myorder")
public class MyOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int uid;
    private String rid;
    private String startTime;
    private String endTime;
    private String state;
    private String menus_id;
    private String menus_num;
    private String tgtAddress;
    private double price;
    private String payTime;
    private String deliverTime;

    public MyOrder(){
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
    public String getRid(){
        return this.rid;
    }

    public void setRid(String rid){
        this.rid = rid;
    }

    @Column(nullable = false)
    public String getMenus_id(){
        return this.menus_id;
    }

    public void setMenus_id(String menus_id){
        this.menus_id = menus_id;
    }

    @Column(nullable = false)
    public String getMenus_num(){
        return this.menus_num;
    }

    public void setMenus_num(String menus_num){
        this.menus_num = menus_num;
    }

    @Column(nullable = false)
    public String getStartTime(){
        return this.startTime;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    @Column
    public String getEndTime(){
        return this.endTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    @Column(nullable = false)
    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }

    @Column(nullable = false)
    public String getTgtAddress(){
        return this.tgtAddress;
    }

    public void setTgtAddress(String tgtAddress){
        this.tgtAddress = tgtAddress;
    }

    @Column(nullable = false)
    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    @Column
    public String getPayTime(){
        return this.payTime;
    }

    public void setPayTime(String payTime){
        this.payTime = payTime;
    }

    @Column
    public String getDeliverTime(){
        return this.deliverTime;
    }

    public void setDeliverTime(String deliverTime){
        this.deliverTime = deliverTime;
    }

}
