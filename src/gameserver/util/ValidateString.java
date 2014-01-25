package gameserver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对字符串进行校验
 * 
 * @author caoxin
 */
public class ValidateString {

    private static final Pattern mailPattern = Pattern.compile("(?:\\w[-.\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)");
    
    public static Matcher getMailMatcher(String mailStr) {
        return mailPattern.matcher(mailStr);
    }
    
    public static boolean isMailStr(String mailStr) {
        return getMailMatcher(mailStr).matches();
    }
}