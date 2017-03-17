package cn.succy.chat.service;


import cn.succy.chat.common.model.User;
import cn.succy.chat.common.util.Constant;
import cn.succy.chat.common.util.EncryptorUtils;
import cn.succy.chat.common.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author Succy
 * @date 2017/3/17 9:19
 */
public class UserService {
    /**
     * 根据用户账号和用户密码进行登录.
     * 应该在controller层就要做好参数校验，service层将不做校验
     *
     * @param acct 用户账号
     * @param pwd  用户密码
     * @return 登录成功返回true, 反之返回false
     */
    public boolean login(int acct, String pwd) {
        // 加盐md5操作
        pwd = EncryptorUtils.md5(Constant.SALT, pwd);
        String sql = User.dao.getSql("findUserByAcctAndPwd");
        User.dao.findFirst(sql, acct, pwd);

        return false;
    }

    public JSONObject register(User user) {
        JSONObject json = new JSONObject();
        String phone = user.getPhone();
        String pwd = user.getPwd();
        String nickName = user.getNickname();
        // 校验手机号是否为空
        if (StrKit.isBlank(phone)) {
            json.put("code", Constant.RespCode.IS_EMPTY);
            json.put("msg", "phone number is empty");
            return json;

        }
        // 验证手机号格式是否正确
        if (!StringUtils.isNumber(phone)) {
            json.put("code", Constant.RespCode.PHONE_FORMAT_ERROR);
            json.put("msg", "phone number is not matched phone format");
            return json;
        }
        // 昵称也不能为空
        if (StrKit.isBlank(nickName)) {
            json.put("code", Constant.RespCode.IS_EMPTY);
            json.put("msg", "nickName is empty");
            return json;

        }

        // 验证密码不为空
        if (StrKit.isBlank(pwd)) {
            json.put("code", Constant.RespCode.IS_EMPTY);
            json.put("msg", "pwd is empty");
            return json;

        }
        // 取加密后的pwd
        pwd = EncryptorUtils.md5(Constant.SALT, pwd);
        user.setPwd(pwd);

        String sql = User.dao.getSql("findUserByPhone");
        User existUser = User.dao.findFirst(sql, phone);
        if (existUser != null) {
            json.put("code", Constant.RespCode.ALREADY_EXIST);
            json.put("msg", "phone number already exist");
            return json;
        }
        // 取出count，用作aid
        String countSql = User.dao.getSql("findAllUserCount");
        System.out.println(countSql);
        Record cntRrd = Db.findFirst(countSql);
        System.out.println(cntRrd);
        Integer count = (Integer) cntRrd.get("count");
        user.setAid(count++);
        // 如果手机号码不为空并且格式什么的都正确，密码也不为空
        if (user.save()) {
            json.put("code", Constant.RespCode.OK);
            json.put("msg", "register success!");
            return json;
        } else {
            json.put("code", Constant.RespCode.INSERT_ERROR);
            json.put("msg", "register failure");
            return json;
        }
    }
}
