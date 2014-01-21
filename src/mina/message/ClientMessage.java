package mina.message;

import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author caoxin
 */
public abstract class ClientMessage {

    protected Logger logger = LoggerFactory.getLogger(ClientMessage.class);
    protected ApplicationContext ac;

    public ClientMessage(ApplicationContext ac) {
        this.ac = ac;
    }

    public void read(ByteBuffer byteBuffer) {
        readImp(byteBuffer);
    }

    public boolean canPerform() {
        return true;
    }

    abstract protected void readImp(ByteBuffer byteBuffer);

    abstract public void perform();
}