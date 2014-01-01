package gameserver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class GameServer {

    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
    }
}