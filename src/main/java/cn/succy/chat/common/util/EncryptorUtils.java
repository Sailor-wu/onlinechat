package cn.succy.chat.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 *
 * @author Succy
 * @date 2017/3/17 9:22
 */
public class EncryptorUtils {
    private EncryptorUtils() {

    }

    /**
     * 对指定字符串进行md5加密
     * @param text 指定要加密的字符串
     * @return 返回md5加密后的结果
     */
    public static String md5(String text) {
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder(1024);
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 对指定字符串进行md5加盐加密
     * @param salt 盐
     * @param target 要加密的字符串
     * @return 返回md5加密后的结果
     */
    public static String md5(String salt, String target) {
        return md5(salt + target);
    }
}
