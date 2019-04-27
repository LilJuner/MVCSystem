package userms.dao;

import userms.entity.User;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DAO {
    private List<User> users;//模拟合法用户数据库
    private Connection conn;//jdbc中的连接对象
    private PreparedStatement pstmt;//预处理语句对象
    private ResultSet rs;//结果集对象
    private String sql;//sql语句对象，主要放sql语句
    private Properties p;//属性对象
    private FileInputStream fin;//文件输入流
    //构建DAO的构造方法(连接数据库之前的准备工作)
    public DAO(){
        //设定初始值为null
        String driver = null;
        String url = null;
        String username = null;
        String password = null;
        //jdbc必须要用到try catch异常
        try{
            fin = new FileInputStream("db.dat");//构造文件输入流对象
            p = new Properties();//构造属性对象
            p.load(fin);//读取属性列表
            //获取以上变量的属性
            driver = p.getProperty("driver");
            url = p.getProperty("url");
            username = p.getProperty("username");
            password = p.getProperty("password");
            fin.close();
        }
        catch (Exception e){
            System.out.println("读取文件db.dat错误");
        }
        //连接数据库
        try{
            Class.forName(driver);//通过类名加载类的本身
            // 建立数据库连接
            conn = DriverManager.getConnection(url + "?useSSL=false&serverTimezone=UTC",username,password);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库连接异常");
        }
    }
    //查找用户名和密码相同的用户，进行登录验证
    public boolean findUser(User user){
        sql = "select * from user where username = ? and password = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());//第一个问号进行预处理语句的处理
            pstmt.setString(2,user.getPassword());//同上，处理第二个问号
            rs = pstmt.executeQuery();//执行上述的sql语句，得到一个结果集对象
            if(rs != null && rs.next()){
                close();
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }
    //增加新用户实现注册功能
    public boolean addUser(User user){
        sql = "insert into user value(?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());
            int i = pstmt.executeUpdate();
            if (i != 0){
                close();
                return true;
            }
            else{
                close();
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }
    //查找所有的合法用户
    public List<User> findAllUsers(){
        List<User> list = new LinkedList<User>();
        sql = "select * from user";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                list.add(new User(rs.getString("username"),rs.getString("password")));
            }
        }
        catch (Exception e){

        }
        finally {
            close();
        }
        return list;
    }
    //删除用户的方法
    public boolean deleteUser(User user){
        sql = "delete from user where username = ? and password = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());
            int i = pstmt.executeUpdate();
            if(i != 0){
                close();
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }
    //实现更改密码的功能
    public boolean changePassword(User user,String newpassword){
        sql = "select * from user where username = ? and password = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());
            rs = pstmt.executeQuery();
            if (rs != null && (!rs.next())){
                close();
                return false;
            }
            sql = "update user set password = ? where username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,newpassword);
            pstmt.setString(2,user.getUsername());
            int i = pstmt.executeUpdate();
            if (i > 0){
                close();
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }
    //关闭资源，JDBC代码最后都要关闭资源，否则会产生临时文件，占用系统内存
    public void close(){
        try {
            if (rs != null){
                rs.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
