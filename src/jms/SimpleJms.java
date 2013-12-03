package jms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 使用JMS服务器范例
 * @author caoxin
 */
public class SimpleJms {

    public static void main(String... args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
        ac.getBean(JmsManager.class).startJmsService();// jms 服务器已经调试成功了
    }
}