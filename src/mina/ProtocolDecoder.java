package mina;

import mina.message.ClientMessage;
import mina.message.MessageManagement;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolDecoder implements MessageDecoder {
    
    private Logger logger = LoggerFactory.getLogger(ProtocolDecoder.class);

    /**
     * 判断接受到二进制流,是否可以进行解析
     * 
     * @param session
     * @param in
     * @return 
     */
    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        if (in.remaining() < 6) {
            return MessageDecoderResult.NOT_OK;
        }
        in.getShort();
        if (in.remaining() < in.getInt()) {
            return MessageDecoderResult.NOT_OK;
        }
        return MessageDecoderResult.OK;
    }

    /**
     * 解析接受客户端的二进制流
     * 
     * @param ioSession
     * @param in
     * @param out
     * @return
     * @throws Exception 
     */
    @Override
    public MessageDecoderResult decode(IoSession ioSession, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        short messageId = in.getShort();
        in.getInt();
        ClientMessage clientMessage =MessageManagement.getMessageByOpcode(messageId);
        clientMessage.read(in.buf());
        out.write(clientMessage);
        return MessageDecoderResult.OK;
    }

    /**
     * 完成解析工作
     * 
     * @param session
     * @param out
     * @throws Exception 
     */
    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
    }
}