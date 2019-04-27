package userms.view;

import userms.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UI {
    public Map doInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("==========用户登录系统==========");
        System.out.println("\t1.注册");
        System.out.println("\t2.登录");
        System.out.println("\t3.查看");
        System.out.println("\t4.删除");
        System.out.println("\t5.修改密码");
        System.out.println("\t0.退出");
        System.out.println("================================");
        System.out.print("请输入你的选择(0-5):");

        int choice = sc.nextInt();

        String username = null;
        String password = null;

        if ((choice != 0) && (choice != 3))
        {
            System.out.print("请输入用户名: ");
            username = sc.next();
            System.out.print("请输入密码: ");
            password = sc.next();
        }

        String newpassword = null;
        if (choice == 5){
            System.out.print("请输入要更改的新密码: ");
            newpassword = sc.next();
        }

        User user = new User(username, password);
        String action = null;

        switch (choice){
            case 0:
                action = "退出";
                break;
            case 1:
                action = "注册";
                break;
            case 2:
                action = "登录";
                break;
            case 3:
                action = "查看";
                break;
            case 4:
                action = "删除";
                break;
            case 5:
                action = "修改";
                break;
        }

        Map m = new HashMap();
        m.put("user", user);
        m.put("action", action);
        m.put("newpassword", newpassword);

        return m;
    }

    public void doOutput(Map m){
        User user = (User)(m.get("user"));
        boolean succ = (Boolean)(m.get("succ"));
        String action = (String)(m.get("action"));

        if ("查看".equals(action)){
            List<User> users = (List<User>)m.get("users");
            System.out.println("系统所有用户如下：");
            for (int i = 0; i < users.size(); i++){
                System.out.println(users.get(i).getUsername());
            }
        }
        else if (succ){
            System.out.println("恭喜你，" + user.getUsername() + action + "成功! ");
        }
        else {
            System.out.println("抱歉, " + user.getUsername() + action + "失败! ");
        }
    }
}
