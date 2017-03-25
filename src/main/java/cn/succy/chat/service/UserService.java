package cn.succy.chat.service;


import cn.succy.chat.common.model.User;
import cn.succy.chat.common.util.Constant;
import cn.succy.chat.common.util.Constant.RespCode;
import cn.succy.chat.common.util.Encodor;
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
     *
     * @param acct 用户账号
     * @param pwd  用户密码
     * @return 登录成功返回true, 反之返回false
     */
    public JSONObject login(int acct, String pwd) {
        JSONObject json = new JSONObject();
        // 加盐md5操作
        pwd = EncryptorUtils.md5(Constant.SALT, pwd);
        String sql = User.dao.getSql("findUserByAcctAndPwd");
        User user = User.dao.findFirst(sql, acct, pwd);
        if (user != null) {
            boolean isLogin = user.getIsLogin();
            if (isLogin) {
                json.put(Constant.RESP_CODE, RespCode.ALREADY_LOGIN);
                json.put(Constant.RESP_MSG, "the user is already login");
                return json;
            }
            if (user.setIsLogin(true).update()) {
                json.put(Constant.RESP_CODE, RespCode.OK);
                json.put(Constant.RESP_MSG, "login success");
                json.put(Constant.RESP_RESULT, user);
                return json;
            }
        }
        json.put(Constant.RESP_CODE, RespCode.NOT_FOUND);
        json.put(Constant.RESP_MSG, "login fail,maybe acct or pwd error");
        json.put(Constant.RESP_RESULT, null);
        return json;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    public JSONObject register(User user) {
        JSONObject json = new JSONObject();
        String phone = user.getPhone();
        String pwd = user.getPwd();
        String nickName = user.getNickname();
        // 校验手机号是否为空
        if (StrKit.isBlank(phone)) {
            json.put(Constant.RESP_CODE, Constant.RespCode.IS_EMPTY);
            json.put(Constant.RESP_MSG, "phone number is empty");
            return json;
        }
        // 验证手机号格式是否正确
        if (!StringUtils.isNumber(phone)) {
            json.put(Constant.RESP_CODE, Constant.RespCode.PHONE_FORMAT_ERROR);
            json.put(Constant.RESP_MSG, "phone number is not matched phone format");
            return json;
        }
        // 昵称也不能为空
        if (StrKit.isBlank(nickName)) {
            json.put(Constant.RESP_CODE, Constant.RespCode.IS_EMPTY);
            json.put(Constant.RESP_MSG, "nickName is empty");
            return json;
        }
        // 防止xss注入攻击
        nickName = Encodor.encodeHtml(nickName);
        user.setNickname(nickName);
        // 验证密码不为空
        if (StrKit.isBlank(pwd)) {
            json.put(Constant.RESP_CODE, Constant.RespCode.IS_EMPTY);
            json.put(Constant.RESP_MSG, "pwd is empty");
            return json;

        }
        // 取加密后的pwd
        pwd = EncryptorUtils.md5(Constant.SALT, pwd);
        user.setPwd(pwd);

        String sql = User.dao.getSql("findUserByPhone");
        User existUser = User.dao.findFirst(sql, phone);
        if (existUser != null) {
            json.put(Constant.RESP_CODE, Constant.RespCode.ALREADY_EXIST);
            json.put(Constant.RESP_MSG, "phone number already exist");
            return json;
        }
        // 取出count，用作aid
        String countSql = User.dao.getSql("findAllUserCount");
        Record cntRrd = Db.findFirst(countSql);
        int count = cntRrd.getNumber("count").intValue();
        int acct = Constant.BASE_AID + (++count);
        user.setAid(acct);
        // 如果手机号码不为空并且格式什么的都正确，密码也不为空
        if (user.save()) {
            json.put(Constant.RESP_CODE, Constant.RespCode.OK);
            json.put(Constant.RESP_MSG, "register success!");

            JSONObject result = new JSONObject();
            result.put(Constant.USER_ACCT, acct);
            result.put(Constant.USER_NICKNAME, nickName);
            json.put(Constant.RESP_RESULT, result);
            return json;
        } else {
            json.put(Constant.RESP_CODE, Constant.RespCode.INSERT_ERROR);
            json.put(Constant.RESP_MSG, "register failure");
            return json;
        }
    }

    public boolean logout(User user) {
        return user.setIsLogin(false).update();
    }
}
