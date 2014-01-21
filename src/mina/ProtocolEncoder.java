package mina;

import mina.message.ServerMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class ProtocolEncoder implements MessageEncoder<ServerMessage> {

    @Override
    public void encode(IoSession session, ServerMessage serverMessage, ProtocolEncoderOutput output) throws Exception {
        IoBuffer ioBuffer = IoBuffer.allocate(100).setAutoExpand(true);
        IoBuffer databuf = serverMessage.write(ioBuffer);
        buf.putShort((short) (1000 + message.getTag()));
        buf.putInt(message.getLength());
        buf.put(databuf);
        buf.flip();
        output.write(buf);
        output.flush();
    }
}