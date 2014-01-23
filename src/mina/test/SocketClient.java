package mina.test;

import java.io.BufferedReader;
import java.io.InputStream;
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
        BufferedReader outBufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] cmd = outBufferedReader.readLine().split("\\s");
        int length = cmd.length;
        if (length > 0 && "help".equals(cmd[0])) {
            System.out.println("use : {ip}, {port}, {opcode} if {ip} is null or {*} default '127.0.0.1'");
            System.exit(1);
        }
        if (length == 0) {
            ip = "127.0.0.1";
            port = 8000;
            opcode = 1;
        } else if (length == 2) {
            ip = "127.0.0.1";
            port = new Integer(cmd[0]);
            opcode = new Integer(cmd[1]);
        } else if (length == 3) {
            ip = cmd[0];
            port = new Integer(cmd[1]);
            opcode = new Integer(cmd[2]);
        }
        Socket socket = new Socket(ip, port);
        OutputStream outputStream = socket.getOutputStream();
        ByteBuffer outputByteBuffer = ByteBuffer.allocate(256);
        InputStream inputStream = socket.getInputStream();
        String outputStr;
        ByteBuffer inputByteBuffer = ByteBuffer.allocate(256);
        while (true) {
            if ((outputStr = outBufferedReader.readLine()) != null) {
                outputByteBuffer.putShort((short) opcode);
                outputByteBuffer.putInt(outputStr.length());
                outputByteBuffer.putInt(outputStr.length());
                for (int i = 0; i < outputStr.length(); i++) {
                    outputByteBuffer.put((byte) (outputStr.charAt(i)));
                }
                if (outputByteBuffer.hasArray()) {
                    byte[] b = Arrays.copyOfRange(outputByteBuffer.array(), outputByteBuffer.arrayOffset(), outputByteBuffer.position());
                    outputStream.write(b);
                }
                outputByteBuffer.clear();
            }
            if (inputStream.read(inputByteBuffer.array()) > 0) {
                String s = "";
                inputByteBuffer.position(0);
                int inputOpcode = inputByteBuffer.getShort();
                int inputLen = inputByteBuffer.getInt();
                for (int i = 0; i < inputLen; i++) {
                    char c = inputByteBuffer.getChar();
                    s += c;
                }
                System.out.println(inputOpcode + "\t" + inputLen + "\t" + s);
                inputByteBuffer.clear();
            }
        }
    }
}