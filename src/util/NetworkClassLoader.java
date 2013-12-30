package util;

/**
 * 无实际意义
 * 
 * @author caoxin
 */
public class NetworkClassLoader extends ClassLoader {

    private String host;
    private int port;

    /**
     *
     * @param name 二进制名称
     * @return Class 远程类的Class对象
     */
    @Override
    public Class findClass(String name) {
        byte[] fileInputStream = loadClassData(name);
        return defineClass(name, fileInputStream, 0, fileInputStream.length);
    }

    /**
     *
     * @param name
     * @return 
     */
    private byte[] loadClassData(String name) {
        String path = FileRead.fileNameCurrentPlatform(name);
        return FileRead.readFileAsByteArray(path);
    }
}