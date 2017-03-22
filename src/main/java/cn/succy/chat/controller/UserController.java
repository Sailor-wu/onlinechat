package cn.succy.chat.controller;

import cn.succy.chat.common.model.User;
import cn.succy.chat.common.util.Constant;
import cn.succy.chat.common.util.Constant.RespCode;
import cn.succy.chat.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;

import javax.servlet.http.HttpSession;

/**
 * @author Succy
 * @date 2017/3/16 17:40
 */
public class UserController extends Controller {
    UserService service = new UserService();

    /**
     * 用户登录接口
     * url: http://localhost:port/user/login
     */
    @Before(POST.class)
    public void login() {
        JSONObject json = new JSONObject();
        Integer acct = getParaToInt("acct", 0);
        String pwd = getPara("pwd");
        if (acct == 0 || StrKit.isBlank(pwd)) {
            json.put(Constant.RESP_CODE, RespCode.ARGS_ERROR);
            json.put(Constant.RESP_MSG, "args error!");
            renderJson(json);
            return;
        }
        JSONObject result = service.login(acct, pwd);
        int respCode = result.getIntValue(Constant.RESP_CODE);
        if (respCode != RespCode.OK) {
            result.remove(Constant.RESP_RESULT);
            renderJson(result);
            return;
        }
        HttpSession session = getSession();
        // 设置半个小时的有效session时间
        session.setMaxInactiveInterval(30);
        session.setAttribute("user", result.getObject(Constant.RESP_RESULT, User.class));

        result.remove(Constant.RESP_RESULT);
        renderJson(result);
    }

    /**
     * 用户注册接口
     * url:http://localhost:port/user/register
     */
    @Before(POST.class)
    public void register() {
        User user = getModel(User.class, "");
        System.out.println("user-> " + user);
        JSONObject result = service.register(user);

        System.out.println(result);

        renderJson(result);
    }

    /**
     * 退出登录接口
     * url:http://localhost:port/user/logout
     */
    public void logout() {
        HttpSession session = getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                // session 还没失效
                if (service.logout(user)) {
                    // 目前先全部清空内存中的session，如果后期有其他地方用到session，并且不应该清空的话再修改
                    session.invalidate();
                }
            }
        }
        // 不管是session自己失效还是手动触发登出失效，最后都进入login页面
        render("login.html");
    }
}
