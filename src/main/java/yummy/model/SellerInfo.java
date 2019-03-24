package yummy.model;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity(name="sellerinfo")
public class SellerInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private String rid;
    private String rname;
    private String type;
    private String location;
    private boolean isChecked;

    public SellerInfo(){
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
    public String getRid(){ return this.rid; }

    public void setRid(String rid){ this.rid = rid; }

    @Column(nullable = false)
    public String getRname(){ return this.rname; }

    public void setRname(String rname){ this.rname = rname; }

    @Column(nullable = false)
    public String getType(){ return this.type; }

    public void setType(String type){ this.type = type; }

    @Column(nullable = false)
    public String getLocation (){ return this.location; }

    public void setLocation(String location){ this.location = location; }

    @Column(nullable = false)
    public boolean getIsChecked(){ return this.isChecked; }

    public void setIsChecked(boolean isChecked){ this.isChecked = isChecked; }
}

