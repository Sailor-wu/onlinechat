package cn.succy.chat.config;

import cn.succy.chat.controller.IndexController;
import cn.succy.chat.controller.UserController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

/**
 * @author Succy
 * @date 2017/3/16 15:56
 */
public class ChatConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants constants) {
        PropKit.use("config.properties");
        constants.setDevMode(PropKit.getBoolean("devMode", false));
    }

    @Override
    public void configRoute(Routes routes) {
        routes.add("/", IndexController.class, "/html");
        routes.add("/user", UserController.class, "/html");
    }

    @Override
    public void configEngine(Engine engine) {

    }

    @Override
    public void configPlugin(Plugins plugins) {
        DruidPlugin druidPlugin = createDruidPlugin();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);

        plugins.add(druidPlugin);
        plugins.add(arp);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    @Override
    public void configHandler(Handlers handlers) {

    }

    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("pwd"));
    }
}
