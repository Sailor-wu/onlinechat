package cn.succy.chat.listener;

import cn.succy.chat.common.model.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听器
 *
 * @author Succy
 * @date 2017-03-18 12:18
 **/

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 将登录状态改成未登录
            user.setIsLogin(false).update();
        }
    }
}
