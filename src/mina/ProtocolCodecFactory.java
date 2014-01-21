package mina;

import mina.message.ServerMessage;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class ProtocolCodecFactory extends DemuxingProtocolCodecFactory {

    public ProtocolCodecFactory(MessageDecoder decoder, MessageEncoder<ServerMessage> encoder) {
        addMessageDecoder(decoder);
        addMessageEncoder(ServerMessage.class, encoder);
    }
}