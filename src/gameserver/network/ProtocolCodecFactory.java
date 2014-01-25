package gameserver.network;

import gameserver.network.core.BaseServerPacket;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class ProtocolCodecFactory extends DemuxingProtocolCodecFactory {

    public ProtocolCodecFactory(MessageDecoder decoder, MessageEncoder<BaseServerPacket> encoder) {
        addMessageDecoder(decoder);
        addMessageEncoder(BaseServerPacket.class, encoder);
    }
}