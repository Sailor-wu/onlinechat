package cn.succy.chat.common.util;

/**
 * 常量类
 * @author Succy
 * @date 2017/3/17 9:26
 */
public class Constant {
    private Constant(){}

    /**
     * 用作md5加密的盐
     */
    public static final String SALT = "Su@@y0_#";

    /**
     * 基准aid，作为用户账号id，以100000开始，多一个就新增1
     **/
    public static final int BASE_AID = 100000;

    public static final String RESP_CODE = "code";
    public static final String RESP_MSG = "msg";
    public static final String RESP_RESULT = "result";


    /**
     * 定义响应码，作为响应客户端json请求时候的响应码
     */
    public static final class RespCode {
        public static final int OK = 200;
        public static final int NOT_FOUND = 404;
        public static final int ERROR = -1;
        public static final int ARGS_ERROR = -2;
        public static final int IS_EMPTY = 600;
        public static final int ALREADY_EXIST = 601;
        public static final int PHONE_FORMAT_ERROR = 603;
        public static final int EMAIL_FORMAT_ERROR = 604;
        public static final int INSERT_ERROR = 605;
        public static final int UPDATE_ERROR = 606;
        public static final int DELETE_ERROR = 607;
        public static final int SELECT_ERROR = 608;
        public static final int ALREADY_LOGIN = 609;


    }

}
