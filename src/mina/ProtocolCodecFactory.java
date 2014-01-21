package mina;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class ProtocolCodecFactory extends DemuxingProtocolCodecFactory {

    public ProtocolCodecFactory(MessageDecoder decoder, MessageEncoder<AbstractMessage> encoder) {
        addMessageDecoder(decoder);
        addMessageEncoder(AbstractMessage.class, encoder);
    }
}