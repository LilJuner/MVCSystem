package userms.service;

import userms.dao.DAO;
import userms.entity.User;

import java.util.List;

public class Service {
    private DAO dao;
    //创建构造方法
    public Service(){
        dao = new DAO();
    }
    //登录
    public boolean login(User user){
        return dao.findUser(user);
    }
    //注册
    public boolean register(User user){
        return dao.addUser(user);
    }

    public List<User> listAll(){
        return dao.findAllUsers();
    }
    //删除
    public boolean deleteUser(User user){
        return dao.deleteUser(user);
    }
    //更改密码
    public boolean changePassword(User user, String newpassword){
        return dao.changePassword(user, newpassword);
    }

}
