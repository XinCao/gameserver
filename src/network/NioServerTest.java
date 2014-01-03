package network;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class NioServerTest {
    
    public static void main(String ...args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("config/nio_server.xml");
        NioServer nioServer = ac.getBean(NioServer.class);
        nioServer.startNioServer();
    }
}