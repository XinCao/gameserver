package mina.message;

import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class LoginClientPacket extends ClientPacket {

    private String str;

    @Override
    protected void readImp(ByteBuffer byteBuffer) {
        StringBuilder sb = new StringBuilder();
        int lenght = byteBuffer.getInt();
        for (int i = 0; i < lenght; i++) {
            sb.append((char)(byteBuffer.get()));
        }
        this.str = sb.toString();
    }

    @Override
    public boolean canPerform() {
        return super.canPerform();
    }

    @Override
    public void perform() {
        System.out.println(this.str);
    }
}