package memcached;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class MemcachedTest {

    public static void main(String ...args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/memcached.xml");
        AccountUseMemcached accountUseMemcached = ac.getBean(AccountUseMemcached.class);
        Account account = new Account();
        account.setId(1);
        account.setName("caoxin");
        account.setShowName("hello world!");
        accountUseMemcached.putAccount(account);
        System.out.println(accountUseMemcached.getAccount("caoxin"));
    }
}
