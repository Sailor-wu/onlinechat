package cn.succy.chat.common.util;

import com.jfinal.kit.StrKit;

/**
 * 编解码处理类
 *
 * @author Succy
 * @date 2017-03-25 10:38
 **/

public class Encodor {
    private Encodor() {
    }

    public static String encodeHtml(String html) {
        if (StrKit.isBlank(html)) {
            return html;
        }
        StringBuilder sb = new StringBuilder(html.length() + 16);
        for (int i = 0; i < html.length(); i++) {
            char c = html.charAt(i);
            switch (c) {
                case '&': {
                    sb.append("&amp;");
                    break;
                }
                //解决办法：将空格转为&nbsp;
                case ' ': {
                    sb.append("&nbsp;");
                    break;
                }
                case '<': {
                    sb.append("&lt;");
                    break;
                }
                case '>': {
                    sb.append("&gt;");
                    break;
                }
                case '\'': {
                    sb.append("&#39;");
                    break;
                }
                case '\\': {
                    sb.append("&#92;");
                    break;
                }
                case '"': {
                    sb.append("&quot;");
                    break;
                }
                case '\n': {
                    sb.append("<br/>");
                    break;
                }
                case '\r': {
                    break;
                }
                case '\t': {
                    break;
                }
                default: {
                    sb.append(c);
                }
            }

        }
        return sb.toString();
    }
}
