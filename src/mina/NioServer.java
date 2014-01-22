package mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.DefaultSocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 *
 * @author caoxin
 */
public class NioServer {

    public static void main(String... args) throws IOException {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ProtocolCodecFactory(new ProtocolDecoder(), new ProtocolEncoder())));
        DefaultSocketSessionConfig cfg = (DefaultSocketSessionConfig) acceptor.getSessionConfig();
        cfg.setTcpNoDelay(true);
        acceptor.setReuseAddress(true);
        cfg.setIdleTime(IdleStatus.BOTH_IDLE, 5);
        acceptor.setHandler(new PacketHandler());
        acceptor.bind(new InetSocketAddress(8000));
    }
}