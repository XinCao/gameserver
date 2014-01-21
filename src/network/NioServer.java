package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import network.options.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NioServer {
    
    private static final Logger logger = LoggerFactory.getLogger(NioServer.class);
    private int readWriteThreads;
    private final DisconnectionThreadPool dcPool;
    private ServerCfg[] cfgs;
    
    private final List<SelectionKey> serverChannelKeys = new ArrayList<SelectionKey>();
    private Dispatcher acceptDispatcher;
    private Dispatcher[] readWriteDispatchers;
    private int currentReadWriteDispatcher;

    /**
     * 使用单通道多复用实现的非阻塞IO服务器
     * 
     * @param readWriteThreads 都写线程个数
     * @param dcPool 断开连接线程池
     * @param cfgs 服务器配置
     */
    public NioServer(int readWriteThreads, DisconnectionThreadPool dcPool, ServerCfg... cfgs) {
        if (Assertion.NetworkAssertion) {
            boolean assertionEnabled = false;
            assert assertionEnabled = true;
            if (!assertionEnabled) {
                throw new RuntimeException("This is unstable build. Assertion must be enabled! Add -ea to your start script or consider using stable build instead.");
            }
        }
        this.readWriteThreads = readWriteThreads;
        this.dcPool = dcPool;
        this.cfgs = cfgs;
    }

    public void startNioServer() {
        try {
            this.initDispatchers(this.readWriteThreads, this.dcPool);
            for (ServerCfg cfg : cfgs) {
                ServerSocketChannel serverChannel = ServerSocketChannel.open();
                serverChannel.configureBlocking(false);
                InetSocketAddress isa;
                if ("*".equals(cfg.hostName)) {
                    isa = new InetSocketAddress(cfg.port);
                    logger.info("Server listening on all available IPs on Port " + cfg.port + " for " + cfg.connectionName);
                } else {
                    isa = new InetSocketAddress(cfg.hostName, cfg.port);
                    logger.info("Server listening on IP: " + cfg.hostName + " Port " + cfg.port + " for " + cfg.connectionName);
                }
                serverChannel.socket().bind(isa);
                SelectionKey acceptKey = this.acceptDispatcher.register(serverChannel, SelectionKey.OP_ACCEPT, new Acceptor(cfg.factory, this));
                serverChannelKeys.add(acceptKey);
            }
        } catch (Exception e) {
            logger.error("NioServer Initialization Error: " + e, e);
            throw new Error("NioServer Initialization Error!");
        }
    }

  /**
   * 获得读写分配器（带有通道负载均衡）
   * @return 
   */
    public final Dispatcher getReadWriteDispatcher() {
        if (readWriteDispatchers == null) {
            return acceptDispatcher;
        }
        if (readWriteDispatchers.length == 1) {
            return readWriteDispatchers[0];
        }
        if (currentReadWriteDispatcher >= readWriteDispatchers.length) {
            currentReadWriteDispatcher = 0;
        }
        return readWriteDispatchers[currentReadWriteDispatcher++];
    }

    /**
     *  初始化所有分配器
     *
     * @param readWriteThreads
     * @param dcPool
     * @throws IOException
     */
    private void initDispatchers(int readWriteThreads, DisconnectionThreadPool dcPool) throws IOException {
        if (readWriteThreads <= 1) {
            acceptDispatcher = new AcceptReadWriteDispatcherImpl("AcceptReadWrite Dispatcher", dcPool);
            acceptDispatcher.start();
        } else {
            acceptDispatcher = new AcceptDispatcherImpl("Accept Dispatcher");
            acceptDispatcher.start();
            readWriteDispatchers = new Dispatcher[readWriteThreads];
            for (int i = 0; i < readWriteDispatchers.length; i++) {
                readWriteDispatchers[i] = new AcceptReadWriteDispatcherImpl("ReadWrite-" + i + " Dispatcher", dcPool);
                readWriteDispatchers[i].start();
            }
        }
    }

    /**
     * 关机服务（1.关闭接收连接 2.执行当前连接对象的onServerClose方法 3.关闭读写消息 4.执行断开连接任务（暂时没有实现））
     */
    public final void shutdown() {
        logger.info("Closing ServerChannels...");
        try {
            for (SelectionKey key : serverChannelKeys) {
                key.cancel();
            }
            logger.info("ServerChannel closed.");
        } catch (Exception e) {
            logger.error("Error during closing ServerChannel, " + e, e);
        }
        this.notifyServerClose();
        try {
            Thread.sleep(1000);
        } catch (Throwable t) {
            logger.warn("Nio thread was interrupted during shutdown", t);
        }
        logger.info(" Active connections: " + getActiveConnections());
        logger.info("Forced Disconnecting all connections...");
        this.closeAll();
        logger.info(" Active connections: " + getActiveConnections());
        List<AConnection> connectionList = getAConnectionList();
        dcPool.waitForDisconnectionTasks(connectionList);
        try {
            Thread.sleep(1000);
        } catch (Throwable t) {
            logger.warn("Nio thread was interrupted during shutdown", t);
        }
    }

    /**
     * 调用所用connection的onServerClose方法
     */
    private void notifyServerClose() {
        if (readWriteDispatchers != null) {
            for (Dispatcher d : readWriteDispatchers) {
                for (SelectionKey key : d.getSelector().keys()) {
                    if (key.attachment() instanceof AConnection) {
                        ((AConnection) key.attachment()).onServerClose();
                    }
                }
            }
        } else {
            for (SelectionKey key : acceptDispatcher.getSelector().keys()) {
                if (key.attachment() instanceof AConnection) {
                    ((AConnection) key.attachment()).onServerClose();
                }
            }
        }
    }

    /**
     * 关闭读写消息
     */
    private void closeAll() {
        if (readWriteDispatchers != null) {
            for (Dispatcher d : readWriteDispatchers) {
                for (SelectionKey key : d.getSelector().keys()) {
                    if (key.attachment() instanceof AConnection) {
                        ((AConnection) key.attachment()).close(true);
                    }
                }
            }
        } else {
            for (SelectionKey key : acceptDispatcher.getSelector().keys()) {
                if (key.attachment() instanceof AConnection) {
                    ((AConnection) key.attachment()).close(true);
                }
            }
        }
    }

    /**
     * 获得当前的连接数
     */
    public final int getActiveConnections() {
        int count = 0;
        if (readWriteDispatchers != null) {
            for (Dispatcher d : readWriteDispatchers) {
                count += d.getSelector().keys().size();
            }
        } else {
            count = acceptDispatcher.getSelector().keys().size() - serverChannelKeys.size();
        }
        return count;
    }
    
    private List<AConnection> getAConnectionList() {
        List<AConnection> connectionList = new ArrayList<AConnection>();
        if (readWriteDispatchers != null) {
            for (Dispatcher d : readWriteDispatchers) {
                for (SelectionKey key : d.getSelector().keys()) {
                    if (key.attachment() instanceof AConnection) {
                        connectionList.add((AConnection) key.attachment());
                    }
                }
            }
        } else {
            for (SelectionKey key : acceptDispatcher.getSelector().keys()) {
                if (key.attachment() instanceof AConnection) {
                    connectionList.add((AConnection) key.attachment());
                }
            }
        }
        return connectionList;
    }
    
    public final Dispatcher getAcceptDispatcher() {
        return acceptDispatcher;
    }
}