package resources.network;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 *
 * @author caoxin
 */
public class ConnectionFactoryImp implements ConnectionFactory {

    @Override
    public AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException {
        return new AionConnection(socket, dispatcher);
    }
}
