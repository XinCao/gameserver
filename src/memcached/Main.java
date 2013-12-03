package memcached;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class Main {

    public static void main(String ...args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
        MemcachedClientService<String> mc = ac.getBean(MemcachedClientService.class);
        UseMemcached useMemcached = ac.getBean(UseMemcached.class);
        System.out.println(useMemcached);
    }
}