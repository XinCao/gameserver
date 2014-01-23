package mina.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

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
        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            byteBuffer.putShort((short) opcode);
            byteBuffer.putInt(str.length());
            byteBuffer.putInt(str.length());
            for (int i = 0; i < str.length(); i++) {
                byteBuffer.put((byte)(str.charAt(i)));
            }
            if (byteBuffer.hasArray()) {
                byte[] b = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.position());
                outputStream.write(b);
            }
            byteBuffer.clear();
        }
    }
}