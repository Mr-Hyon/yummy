package yummy.dao;

import yummy.model.User;

import java.util.List;

public interface UserDao {

    public void addUser(User user);

    public void modifyUser(User user);

    public void deleteUser(User user);

    public boolean login(String email,String password);

    public User getUserById(int id);

    public User getUserByEmail(String email);

    public List<User> getAllUser();
}
