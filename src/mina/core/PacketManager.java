package mina.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author caoxin
 */
public class PacketManager implements ApplicationContextAware {

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        PacketManager.ac = ac;
    }

    public static <T extends BasePacket> T getPacketByOpcode(short opcode) {
        BasePacket basePacket = PacketKind.getPacketByOpcode(opcode);
        basePacket.setAc(ac);
        return (T) basePacket;
    }
}