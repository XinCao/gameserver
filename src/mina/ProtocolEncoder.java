package mina;

import mina.message.ServerMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class ProtocolEncoder implements MessageEncoder<ServerMessage> {

    @Override
    public void encode(IoSession session, ServerMessage serverMessage, ProtocolEncoderOutput output) throws Exception {
        IoBuffer ioBuffer = IoBuffer.allocate(256).setAutoExpand(true);
        ioBuffer.putShort(serverMessage.getMessageId());
        IoBuffer data = IoBuffer.allocate(128).setAutoExpand(true);
        serverMessage.write(data);
        data.flip();
        ioBuffer.putInt(data.capacity());
        ioBuffer.put(data);
        ioBuffer.flip();
        output.write(ioBuffer);
        output.flush();
    }
}