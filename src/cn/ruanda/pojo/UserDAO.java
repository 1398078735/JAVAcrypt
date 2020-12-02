package cn.ruanda.pojo;

import java.util.List;

public interface UserDAO {
    public void add(User user);
    public User findByUsername(String username);
}
