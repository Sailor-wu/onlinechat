package cn.succy.chat.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @author Succy
 * @date 2017/3/16 17:59
 */
public class StringUtils {
    private StringUtils() {
    }

    /**
     * 判断一个字符串是否是符合邮箱格式
     *
     * @param str 要判断的字符串
     * @return 如果符合邮箱格式返回true, 反之返回false
     */
    public static boolean isEmail(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9_=\\&\\-\\.\\+]*[a-zA-Z0-9]*@[a-zA-Z0-9][a-zA-Z0-9_=\\-\\.]+[a-zA-Z0-9]$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断一个字符串是否是符合手机号格式
     *
     * @param str 要判断的字符串
     * @return 如果符合手机号码格式返回true, 反之返回false
     */
    public static boolean isPhone(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^1[34578]\\d{9}$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断传递的字符串是否是数字
     * @param str 要判断的字符串
     * @return 如果该字符串是数字，返回true，反之返回false
     */
    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        if (str.split("\\.").length > 2) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (i == 0 && (str.charAt(0) == '-')) {
                continue;
            }
            if ('.' == str.charAt(i)) {
                continue;
            }
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
