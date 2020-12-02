package cn.ruanda.pojo;

public interface UserDAOPlus extends UserDAO {
    public User selectUserByOne(User user);

    public int deleteUserById(User user);

    public int updateUser(User user);

    public int insertUser(User user);
}
