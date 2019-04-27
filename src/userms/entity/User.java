package userms.entity;

public class User {
    //定义两个私有的用户名和密码
    private String username;
    private String password;
    //创建一个无参的构造方法
    public User(){
    }
    //创建一个有参的构造方法
    public User(String username,String password){
        this.username = username;
        this.password = password;
    }
    //创建访问器，来获取用户名
    public String getUsername(){
        return this.username;
    }
    //创建访问器，来获取密码
    public String getPassword(){
        return this.password;
    }
    //比较两个用户名是否有相同的用户名和密码，是返回true，不是则返回false
    public boolean equals(Object o){
        User user = (User) o;

        if(this.username.equals(user.getUsername()) && this.password.equals(user.getPassword())){
            return true;
        }
        else
            return false;
    }
}
