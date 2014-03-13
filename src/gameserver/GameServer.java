package gameserver;

import java.io.IOException;
import gameserver.network.NioServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class GameServer {

    public static void main(String... args) throws IOException {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
        NioServer.startServer();
    }
}