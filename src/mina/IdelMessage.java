/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mina;

import java.nio.ByteBuffer;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class IdelMessage extends AbstractMessage {

    @Override
    public void doAction(IoSession session) {
        session.write(this);
    }

    @Override
    public short getTag() {
        return 120;
    }

    @Override
    public void request(IoSession session, ByteBuffer data) {
    }

    @Override
    public IoBuffer response(IoSession session) {
        response = IoBuffer.allocate(8).setAutoExpand(true);
        this.putShort((short) 1);
        response.flip();
        return response;
    }
}