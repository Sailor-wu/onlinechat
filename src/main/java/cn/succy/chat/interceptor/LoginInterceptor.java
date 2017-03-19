package cn.succy.chat.interceptor;

import cn.succy.chat.common.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpSession;

/**
 * 登录过滤器
 *
 * @author Succy
 * @date 2017-03-18 12:08
 **/

public class LoginInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        HttpSession session = controller.getSession(false);
        if (session == null) {
            controller.render("login.html");
        } else {
            User user = (User) session.getAttribute("user");
            if (user == null)
                controller.render("login.html");
            else
                controller.render("index.html");
        }
    }
}
