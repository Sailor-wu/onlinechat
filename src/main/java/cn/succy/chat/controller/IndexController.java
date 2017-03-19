package cn.succy.chat.controller;

import cn.succy.chat.interceptor.LoginInterceptor;
import cn.succy.chat.validator.LoginValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * @author Succy
 * @date 2017/3/16 16:42
 */
public class IndexController extends Controller {
    @Before(LoginInterceptor.class)
    public void index() {
        render("login.html");
    }
}
