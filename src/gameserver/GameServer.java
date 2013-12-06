package gameserver;

import dao.SimpleDao;
import event.WorldEvents;
import event.dispatcher.GameEventDispatcher;
import jmx.JmxManager;
import memcached.MemcachedClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class GameServer {

    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
        startJmxService(ac);
    }
    
    /**
     * 用于后台管理接口
     * 
     * ApplicationContext spring 容器
     */
    private static void startJmxService(ApplicationContext ac) {
        ac.getBean(JmxManager.class).startJmxService();
    }
}
