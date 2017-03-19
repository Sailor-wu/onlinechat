package cn.succy.chat.config;

import cn.succy.chat.common.model._MappingKit;
import cn.succy.chat.controller.IndexController;
import cn.succy.chat.controller.RobotController;
import cn.succy.chat.controller.UserController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
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
        routes.add("/robot", RobotController.class);
    }

    @Override
    public void configEngine(Engine engine) {

    }

    @Override
    public void configPlugin(Plugins plugins) {
        DruidPlugin druidPlugin = createDruidPlugin();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 使用sql模板，sql语句将不写在代码中。
        arp.setBaseSqlTemplatePath(PathKit.getRootClassPath());
        arp.addSqlTemplate("user.sql");
        // 设置显示sql
        arp.setShowSql(PropKit.getBoolean("showSql", false));

        // 自动添加映射
        _MappingKit.mapping(arp);
        // 添加redis的支持
        //RedisPlugin redisPlugin = new RedisPlugin("chat", "127.0.0.1");

        plugins.add(druidPlugin);
        plugins.add(arp);
        //plugins.add(redisPlugin);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        // 全局的拦截器才用在这里配置
    }

    @Override
    public void configHandler(Handlers handlers) {

    }

    /**
     * 构造druid连接池插件对象
     */
    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("pwd"));
    }
}
