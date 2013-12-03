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
        ac.getBean(JmxManager.class).startJmxService();// jms 服务器已经调试成功了
        GameEventDispatcher dispatcher = ac.getBean(GameEventDispatcher.class);
        dispatcher.triggerEvent(WorldEvents.login);
        SimpleDao simpleDao = ac.getBean(SimpleDao.class);
        simpleDao.insert();
        MemcachedClientService<String> mc = ac.getBean(MemcachedClientService.class);
        mc.put("caoxin", "jingxin191314@foxmail.com");
        String rs = mc.get("caoxin");
        System.out.println(rs);
    }
}