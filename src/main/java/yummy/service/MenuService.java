package yummy.service;

import yummy.model.Menu;

import java.util.List;

public interface MenuService {

    public void addMenu(Menu menu);

    public List<Menu> getMenusByRid(String rid);

    public Menu getMenuById(int id);

    public void DeleteMenu(Menu menu);

}
