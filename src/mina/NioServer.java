package mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.DefaultSocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class NioServer {

    public static void main(String... args) throws IOException {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
        startServer();
    }

    public static void startServer() throws IOException {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ProtocolCodecFactory(new ProtocolDecoder(), new ProtocolEncoder())));
        ExecutorService filterExecutor = new OrderedThreadPoolExecutor(50);
        acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(filterExecutor));
        DefaultSocketSessionConfig cfg = (DefaultSocketSessionConfig) acceptor.getSessionConfig();
        cfg.setTcpNoDelay(true);
        acceptor.setReuseAddress(true);
        cfg.setIdleTime(IdleStatus.BOTH_IDLE, 5);
        acceptor.setHandler(new PacketHandler());
        acceptor.bind(new InetSocketAddress(8000));
    }
}