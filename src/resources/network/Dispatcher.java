package resources.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import resources.network.options.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Dispatcher extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private final DisconnectionThreadPool dcPool;
    protected Selector selector;
    private final Object gate = new Object();

    public Dispatcher(String name, DisconnectionThreadPool dcPool) throws IOException {
        super(name);
        this.dcPool = dcPool;
        this.selector = SelectorProvider.provider().openSelector();
    }

    abstract void closeConnection(AConnection con);

    abstract void dispatch() throws IOException;

    /**
     * Dispatching Selected keys and processing pending close.
     */
    @Override
    public void run() {
        while(true) {
            try {
                dispatch();
                synchronized (gate) {
                }
            } catch (Exception e) {
                logger.error("Dispatcher error! " + e, e);
            }
        }
    }

    /**
     * 注册一个连接
     * 
     * @param ch
     * @param ops
     * @param att
     * @throws IOException 
     */
    public final void register(SelectableChannel ch, int ops, AConnection att) throws IOException {
        synchronized (gate) {
            selector.wakeup();
            att.setKey(ch.register(selector, ops, att));
        }
    }

    /**
     * 注册ServerChannel
     * 
     * @param ch
     * @param ops
     * @param att
     * @return
     * @throws IOException 
     */
    public final SelectionKey register(SelectableChannel ch, int ops, Acceptor att) throws IOException {
        synchronized (gate) {
            selector.wakeup();
            return ch.register(selector, ops, att);
        }
    }

    /**
     * 创建一个客户端连接
     * 
     * @param key 
     */
    final void accept(SelectionKey key) {
        try {
            ((Acceptor) key.attachment()).accept(key);
        } catch (Exception e) {
            logger.error("Error while accepting connection: +" + e, e);
        }
    }


    final void read(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        AConnection con = (AConnection) key.attachment();
        ByteBuffer rb = con.readBuffer;
        if (Assertion.NetworkAssertion) {
            assert con.readBuffer.hasRemaining();
        }
        int numRead;
        try {
            numRead = socketChannel.read(rb);
        } catch (IOException e) {
            closeConnectionImpl(con);
            logger.info("error happend during read data from " + key.toString());
            return;
        }
        if (numRead == -1) {
            closeConnectionImpl(con);
            return;
        } else if (numRead == 0) {
            rb.clear();
            return;
        }
        rb.flip();
        // 因为协议前两个字段是长度
        // 所以收到一个完整的协议应该是
        // 协议长度 > 2 (可以读出长度) 且 后面的还有大于等于该长度的数据
        for (;;) {
            int remaining = rb.remaining();
            if (remaining <= 2) {
                break;
            }
            short needlen = rb.getShort(rb.position());
            if (remaining < needlen) {
                break;
            }
            if (!parse(con, rb)) {
                closeConnectionImpl(con);
                return;
            }
        }
        if (rb.hasRemaining()) {
            con.readBuffer.compact();
            if (Assertion.NetworkAssertion) {
                assert con.readBuffer.hasRemaining();
            }
        } else {
            rb.clear();
        }
    }


    private boolean parse(AConnection con, ByteBuffer buf) {
        short sz = 0;
        try {
            sz = buf.getShort();
            if (sz > 1) {
                sz -= 2;
            }
            ByteBuffer b = (ByteBuffer) buf.slice().limit(sz);
            b.order(ByteOrder.LITTLE_ENDIAN);
            buf.position(buf.position() + sz);
            return con.processData(b);
        } catch (IllegalArgumentException e) {
            logger.warn("Error on parsing input from client - account: " + con + " packet size: " + sz + " real size:" + buf.remaining(), e);
            return false;
        }
    }

    final void write(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        AConnection con = (AConnection) key.attachment();
        int numWrite;
        ByteBuffer wb = con.writeBuffer;
        if (wb.hasRemaining()) {
            try {
                numWrite = socketChannel.write(wb);
            } catch (IOException e) {
                System.out.println("writeFailed " + e.getMessage());
                closeConnectionImpl(con);
                return;
            }
            if (numWrite == 0) {
                System.out.println("Write " + numWrite + " ip: " + con.getIP());
                logger.info("Write " + numWrite + " ip: " + con.getIP());
                return;
            }
            System.out.println("write " + numWrite);
            if (wb.hasRemaining()) {
                return;
            }
        }

        while (true) {
            wb.clear();
            boolean writeFailed = !con.writeData(wb);
            if (writeFailed) {
                wb.limit(0);
                System.out.println("writeFailed " + wb.limit());
                break;
            }
            try {
                numWrite = socketChannel.write(wb);
            } catch (IOException e) {
                closeConnectionImpl(con);
                System.out.println("writeFailed2 " + wb.limit() + ", " + e.getMessage());
                return;
            }
            if (numWrite == 0) {
                logger.info("Write " + numWrite + " ip: " + con.getIP());
                System.out.println("Write " + numWrite + " ip: " + con.getIP());
                return;
            }
            System.out.println("write1 " + numWrite);
            if (wb.hasRemaining()) {
                System.out.println("writeFailed3 " + wb.remaining());
                return;
            }
        }
        if (Assertion.NetworkAssertion) {
            assert !wb.hasRemaining();
        }
        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
        if (con.isPendingClose()) {
            closeConnectionImpl(con);
        }
    }

    protected final void closeConnectionImpl(AConnection con) {
        if (Assertion.NetworkAssertion) {
            assert Thread.currentThread() == this;
        }
        if (con.onlyClose()) {
            dcPool.scheduleDisconnection(new DisconnectionTask(con), con.getDisconnectionDelay());
        }
    }
    
    public final Selector getSelector() {
        return this.selector;
    }
}