package gameserver;

import java.io.IOException;
import gameserver.network.NioServer;
import gameserver.service.TableService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class GameServer {

    public static void main(String... args) throws IOException {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
        NioServer.startServer();
        TableService tableService = ac.getBean(TableService.class);
        tableService.getTable(TableService.TableKind.ITEM_TEMPLATE).getBaseTableRow(1);
    }
}