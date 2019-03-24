package yummy.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.MenuDao;
import yummy.model.Menu;
import yummy.service.MenuService;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuDao menuDao;

    public void addMenu(Menu menu){
        menuDao.addMenu(menu);
    }

    public List<Menu> getMenusByRid(String rid){
        return menuDao.getMenusByRid(rid);
    }

    public Menu getMenuById(int id){
        return menuDao.getMenuById(id);
    }

    public void DeleteMenu(Menu menu){
        menuDao.deleteMenu(menu);
    }
}
