package mina.message;

import java.nio.ByteBuffer;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author caoxin
 */
public class LoginClientMessage extends ClientMessage {

    public LoginClientMessage(ApplicationContext ac) {
        super(ac);
    }

    @Override
    protected void readImp(ByteBuffer bytebuffer) {
    }

    @Override
    public boolean canPerform() {
        return super.canPerform();
    }

    @Override
    public void perform() {
    }
}