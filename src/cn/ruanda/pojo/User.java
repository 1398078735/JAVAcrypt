package cn.ruanda.pojo;

public class User {
    //写字段属性
    private String Username;
    private String Password;
    private int Age;
    //构造函数
    public User(){};

    public User(String username, String password, int age) {
        Username = username;
        Password = password;
        Age = age;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
