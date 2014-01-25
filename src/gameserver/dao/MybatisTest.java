package gameserver.dao;

import gameserver.dao.model.ItemLog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class MybatisTest {

    public static void main(String[] args) {
        ApplicationContext ac  = new FileSystemXmlApplicationContext("./config/mybatis.xml");
        ItemLogMapper itemLogMapper = (ItemLogMapper) ac.getBean("itemLogMapper");
        ItemLog itemLog = new ItemLog();
        itemLog.setAction("add");
        itemLog.setItemTplId(2000);
        itemLog.setAmount(2);
        itemLog.setChannel(1);
        itemLog.setExtraData("star");
        itemLogMapper.insertItemLog(itemLog);
    }
}