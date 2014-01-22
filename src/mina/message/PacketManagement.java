package mina.message;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author caoxin
 */
public class PacketManagement implements ApplicationContextAware {

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        PacketManagement.ac = ac;
    }

    public static <T extends BasePacket> T getPacketByOpcode(short opcode) {
        BasePacket basePacket = PacketKind.getPacketByOpcode(opcode);
        basePacket.setAc(ac);
        return (T) basePacket;
    }
}