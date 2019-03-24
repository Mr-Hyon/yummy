package yummy.dao;

import yummy.model.Menu;

import java.util.List;

public interface MenuDao {

    public void addMenu(Menu menu);

    public void modifyMenu(Menu menu);

    public void deleteMenu(Menu menu);

    public List<Menu> getMenusByRid(String rid);

    public Menu getMenuById(int id);
}
