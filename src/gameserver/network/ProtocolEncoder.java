package gameserver.network;

import java.util.Arrays;
import gameserver.network.core.BaseServerPacket;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class ProtocolEncoder implements MessageEncoder<BaseServerPacket> {

//    @Override
//    public void encode(IoSession session, BaseServerPacket serverMessage, ProtocolEncoderOutput output) throws Exception {
//        IoBuffer ioBuffer = IoBuffer.allocate(256).setAutoExpand(true);
//        ioBuffer.putShort(serverMessage.getOpcode());
//        ioBuffer.putInt(0);
//        int curPostion = ioBuffer.position();
//        serverMessage.write(ioBuffer);
//        int length = ioBuffer.position() - curPostion;
//        ioBuffer.position(2);
//        ioBuffer.putInt(length);
//        ioBuffer.position(0);
//        byte[] b = Arrays.copyOfRange(ioBuffer.array(), ioBuffer.arrayOffset(), length + 6);
//        output.write(b);
//        output.flush();
//    }
    @Override
    public void encode(IoSession session, BaseServerPacket serverMessage, ProtocolEncoderOutput output) throws Exception {
        IoBuffer ioBuffer = IoBuffer.allocate(256).setAutoExpand(true);
        ioBuffer.putShort(serverMessage.getOpcode());
        ioBuffer.putInt(0);
        int curPostion = ioBuffer.position();
        serverMessage.write(ioBuffer);
        int length = ioBuffer.position() - curPostion;
        ioBuffer.position(2);
        ioBuffer.putInt(length);
        ioBuffer.position(0);
        output.write(ioBuffer);
        output.flush();
    }
}