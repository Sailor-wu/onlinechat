package cn.succy.chat.controller;

import cn.succy.chat.common.model.User;
import cn.succy.chat.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;

/**
 * @author Succy
 * @date 2017/3/16 17:40
 */
public class UserController extends Controller {
    UserService service = new UserService();

    public void login() {
        Integer acct = getParaToInt("acct");
        String pwd = getPara("pwd");
        boolean success = service.login(acct, pwd);


    }

    public void register() {
        User user = getModel(User.class, "");
        System.out.println("user-> " + user);
        JSONObject result = service.register(user);

        System.out.println(result);

        renderJson(result);
    }
}
