package mina;

import mina.message.ServerPacket;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class ProtocolCodecFactory extends DemuxingProtocolCodecFactory {

    public ProtocolCodecFactory(MessageDecoder decoder, MessageEncoder<ServerPacket> encoder) {
        addMessageDecoder(decoder);
        addMessageEncoder(ServerPacket.class, encoder);
    }
}