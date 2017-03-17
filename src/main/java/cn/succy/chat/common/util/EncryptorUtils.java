package cn.succy.chat.common.util;

/**
 * 加密工具类
 * @author Succy
 * @date 2017/3/17 9:22
 */
public class EncryptorUtils {
    private EncryptorUtils() {

    }

    public static String md5(String str) {
        return null;
    }

    public static String md5(String salt, String target) {
        return md5(salt + target);
    }
}
