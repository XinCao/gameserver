package network;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AcceptReadWriteDispatcherImpl extends Dispatcher {

    private final List<AConnection> pendingClose = new ArrayList<AConnection>();

    public AcceptReadWriteDispatcherImpl(String name, DisconnectionThreadPool dcPool) throws IOException {
        super(name, dcPool);
    }

    @Override
    void dispatch() throws IOException {
        int selected = selector.select();
        processPendingClose();
        if (selected != 0) {
            Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey key = selectedKeys.next();
                selectedKeys.remove();
                if (!key.isValid()) {
                    continue;
                }
                switch (key.readyOps()) {
                    case SelectionKey.OP_ACCEPT: {this.accept(key); break;}
                    case SelectionKey.OP_READ: {this.read(key); break;}
                    case SelectionKey.OP_WRITE: {this.write(key); break;}
                    case SelectionKey.OP_READ | SelectionKey.OP_WRITE:  {
                        this.read(key);
                        if (key.isValid()) {
                            this.write(key);
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    void closeConnection(AConnection con) {
        synchronized (pendingClose) {
            pendingClose.add(con);
        }
    }

    private void processPendingClose() {
        synchronized (pendingClose) {
            for (AConnection connection : pendingClose) {
                closeConnectionImpl(connection);
            }
            pendingClose.clear();
        }
    }
}
