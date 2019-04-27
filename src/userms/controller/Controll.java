package userms.controller;

import userms.entity.User;
import userms.service.Service;
import userms.view.UI;

import java.util.List;
import java.util.Map;

public class Controll {
    public static void main(String args[]){
        UI ui = new UI();
        Service service = new Service();
        Map m = null;
        String action = null;
        boolean succ = false;
        User user = null;
        List<User> users = null;

        while (true){
            //调用采购部门采购原材料
            m = ui.doInput();
            action = (String)m.get("action");

            if ("退出".equals(action)){
                System.out.println("欢迎下次使用本系统");
                System.exit(0);//正常退出
            }

            //将原材料交给生产部门去加工，返回成品
            user = (User) m.get("user");
            if ("注册".equals(action)){
                succ = service.register(user);
            }
            else if("登录".equals(action)){
                succ = service.login(user);
            }
            else if ("查看".equals(action)){
                users = service.listAll();
            }
            else if ("删除".equals(action)){
                succ = service.deleteUser(user);
            }
            else if ("修改".equals(action)){
                String newpassword = (String)m.get("newpassword");
                succ = service.changePassword(user, newpassword);
            }

            //将加工完成的商品交给销售部门销售出去
            m.put("succ", new Boolean(succ));
            m.put("users", users);

            ui.doOutput(m);
        }
    }
}
