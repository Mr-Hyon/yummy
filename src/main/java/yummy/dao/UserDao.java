package yummy.dao;

import yummy.model.User;

public interface UserDao {

    public void addUser(User user);

    public void modifyUser(User user);

    public void deleteUser(User user);

    public boolean login(String email,String password);

    public User getUserById(int id);

    public User getUserByEmail(String email);
}
