package util;

import java.util.Properties;

/**
 * 获取应用运行环境信息
 * 
 * @author caoxin
 */
public class CurrentPlatform {
    
    public static String osName = currentOsName();
    public static char pathSeparator = currentPathSeparator();
    public static char fileSeparator = currentFileSeparator();
    public static String lineSeparator = currentLineSeparator();
    public static String classPath = currentClassPath();
    
    private static String currentOsName() {
        return getPlatformInfo("os.name");
    }

    private static char currentPathSeparator() {
        return getPlatformInfo("path.separator").charAt(0);
    }
    
    private static char currentFileSeparator() {
        return getPlatformInfo("file.separator").charAt(0);
    }
    
    private static String currentLineSeparator() {
        return getPlatformInfo("line.separator");
    }
    
    private static String currentClassPath() {
        return getPlatformInfo("java.class.path");
    }
    
    private static String getPlatformInfo(String key) {
        Properties props = System.getProperties();
        return props.getProperty(key);
    }
    
    public static void println(String str) {
        System.out.println(str);
    }
    
    public static void main(String ...args) {
        println(osName);
        println(pathSeparator + "");
        println(fileSeparator + "");
        println(lineSeparator);
        println(classPath);
    }
}