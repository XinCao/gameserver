package jmx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 使用JMX服务器范例
 * 
 * @author caoxin
 */
public class JmxTest {

    public static void main(String... args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/jmx.xml");
    }
}