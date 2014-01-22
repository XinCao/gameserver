package mina.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * @author caoxin
 */
public class SocketClient {

    private static String ip;
    private static int port;
    private static int opcode;

    public static void main(String... args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] cmd = bufferedReader.readLine().split("\\s");
        int length = cmd.length;
        if (length < 2) {
            System.out.println("use : {ip}, {port}, {opcode} if {ip} is null or {*} default '127.0.0.1'");
            System.exit(1);
        }
        if (length == 2) {
            ip = "127.0.0.1";
            port = new Integer(cmd[0]);
            opcode = new Integer(cmd[1]);
        }
        if (length == 3) {
            ip = cmd[0];
            port = new Integer(cmd[1]);
            opcode = new Integer(cmd[2]);
        }
        Socket socket = new Socket(ip, port);
        OutputStream outputStream = socket.getOutputStream();
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        byteBuffer.putShort((short) opcode);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            byteBuffer.putInt(str.length());
            for (int i = 0; i < str.length(); i++) {
                byteBuffer.putChar(str.charAt(i));
            }
            byteBuffer.flip();
            if (byteBuffer.hasArray()) {
                byte[] data = byteBuffer.array();
                outputStream.write(data);
            }
        }
    }
}