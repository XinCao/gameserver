package network;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface ConnectionFactory {

    public AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException;
}
