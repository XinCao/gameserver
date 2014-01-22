package mina;

import mina.message.ServerPacket;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class ProtocolEncoder implements MessageEncoder<ServerPacket> {

    @Override
    public void encode(IoSession session, ServerPacket serverMessage, ProtocolEncoderOutput output) throws Exception {
        IoBuffer ioBuffer = IoBuffer.allocate(256).setAutoExpand(true);
        ioBuffer.putShort(serverMessage.getOpcode());
        IoBuffer data = IoBuffer.allocate(128).setAutoExpand(true);
        serverMessage.write(data);
        ioBuffer.putInt(data.position() - data.arrayOffset());
        System.out.println(data.position() + "\t" + data.arrayOffset() + "\t" + (data.position() - data.arrayOffset()));
        data.flip();
        ioBuffer.put(data);
        ioBuffer.flip();
        output.write(ioBuffer);
        output.flush();
    }
}