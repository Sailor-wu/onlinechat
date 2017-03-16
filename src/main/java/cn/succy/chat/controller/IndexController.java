package cn.succy.chat.controller;

import com.jfinal.core.Controller;

/**
 * @author Succy
 * @date 2017/3/16 16:42
 */
public class IndexController extends Controller {
    public void index() {
        render("index.html");
    }
}
