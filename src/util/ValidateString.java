package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对字符串进行校验
 * 
 * @author caoxin
 */
public class ValidateString {

    /**
     * 邮件正则表达式
     */
    private static final Pattern mailReg = Pattern.compile("(?:\\w[-.\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)");
    
    public static Matcher getMailRegMatcher(String str) {
        return mailReg.matcher(str);
    }
    
    public static boolean isMailReg(String str) {
        return getMailRegMatcher(str).matches();
    }
}