package yummy.service;

import yummy.model.User;

public interface UserService {

    public boolean regist(User user);

    public boolean login(String email,String password);

    public boolean isExist(String email);

    public User getUserByEmail(String email);

    public User getUserById(int id);

    public void updateUser(User user);
}
