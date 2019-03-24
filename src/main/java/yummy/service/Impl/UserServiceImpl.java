package yummy.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.UserDao;
import yummy.model.User;
import yummy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean regist(User user) {
        User result = userDao.getUserByEmail(user.getEmail());
        if(result == null){
            userDao.addUser(user);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean login(String email, String password) {
        return userDao.login(email,password);
    }

    public boolean isExist(String email){
        if(userDao.getUserByEmail(email)!=null){
            return true;
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.modifyUser(user);
    }
}
