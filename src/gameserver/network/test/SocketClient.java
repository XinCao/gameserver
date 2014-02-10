package gameserver.network.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class SocketClient {

    public static void main(String... args) throws Exception {
        BufferedReader cmdBufferReader = new BufferedReader(new InputStreamReader(System.in));
        String[] cmd = cmdBufferReader.readLine().split("\\s");
        String ip = "";
        int port = 0;
        short opcode = 0;
        int length = cmd.length;
        if ((length > 0 && "help".equals(cmd[0])) || length == 0 || cmd[0].equals("")) {
            System.out.println("use : {ip}, {port}, {opcode} if {ip} is null or {*} default '127.0.0.1'");
            System.exit(1);
        } else if (length > 0 && cmd[0].equals("default")) {
            ip = "127.0.0.1";
            port = 8000;
            opcode = 1;
        } else if (length == 1) {
            ip = "127.0.0.1";
            port = 8000;
            opcode = new Short(cmd[0]);
        } else if (length == 2) {
            ip = "127.0.0.1";
            port = new Integer(cmd[0]);
            opcode = new Short(cmd[1]);
        } else if (length == 3) {
            ip = cmd[0];
            port = new Integer(cmd[1]);
            opcode = new Short(cmd[2]);
        }
        Socket socket = new Socket(ip, port);
        OutputStream outputStream = socket.getOutputStream();
        Thread outInfoThread = new Thread(new OutputInfo(outputStream, opcode), "write thread");
        outInfoThread.start();
        InputStream inputStream = socket.getInputStream();
        Thread inputInfoThread = new Thread(new InputInfo(inputStream), "read thread");
        inputInfoThread.start();
    }

    static class OutputInfo implements Runnable {

        private static final Logger logger = LoggerFactory.getLogger(OutputInfo.class);
        private BufferedReader outBufferedReader = new BufferedReader(new InputStreamReader(System.in));
        private OutputStream outputStream;
        private short opcode;

        public OutputInfo(OutputStream outputStream, short opcode) {
            this.outputStream = outputStream;
            this.opcode = opcode;
        }

        @Override
        public void run() {
            ByteBuffer outputByteBuffer = ByteBuffer.allocate(256);
            String outputStr = "Start connect server!";
            logger.info(outputStr);
            while (true) {
                try {
                    if ((outputStr = outBufferedReader.readLine()) != null) {
                        outputByteBuffer.putShort((short) opcode);
                        String[] words = outputStr.split("\\s");
                        int num = words.length;
                        if (num % 2 != 0) {
                            logger.info("Please check the incoming data!");
                            continue;
                        }
                        outputByteBuffer.putInt(0);
                        int curPosition = outputByteBuffer.position();
                        for (int i = 0; i < num; i = i + 2) {
                            if (isInt(words[i])) {
                                outputByteBuffer.putInt(new Integer(words[i + 1]));
                            } else if (isShort(words[i])){
                                outputByteBuffer.putShort(new Short(words[i + 1]));
                            } else if (isString(words[i])) {
                                String s = words[i + 1];
                                outputByteBuffer.putInt(s.length());
                                for (int j = 0; j < s.length(); j++) {
                                    outputByteBuffer.putChar(s.charAt(j));
                                }
                            }
                        }
                        int afterPosition = outputByteBuffer.position();
                        int len = afterPosition - curPosition;
                        outputByteBuffer.position(2);
                        outputByteBuffer.putInt(len);
                        outputByteBuffer.position(afterPosition);
                        if (outputByteBuffer.hasArray()) {
                            byte[] b = Arrays.copyOfRange(outputByteBuffer.array(), outputByteBuffer.arrayOffset(), outputByteBuffer.position());
                            outputStream.write(b);
                        }
                        outputByteBuffer.clear();
                    }
                } catch (IOException ex) {
                    logger.debug(ex.getMessage());
                }
            }
        }
    }
    
    public static boolean isInt(String type) {
        if (type.equalsIgnoreCase("int")) {
            return true;
        }
        return false;
    }
    
    public static boolean isShort(String type) {
        if (type.equalsIgnoreCase("short")) {
            return true;
        }
        return false;
    }
    
    public static boolean isString(String type) {
        if (type.equalsIgnoreCase("string")) {
            return true;
        }
        return false;
    }

    static class InputInfo implements Runnable {

        private static final Logger logger = LoggerFactory.getLogger(InputInfo.class);
        private InputStream inputStream;

        public InputInfo(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            ByteBuffer inputByteBuffer = ByteBuffer.allocate(256);
            logger.info("Begin to accept data!");
            while (true) {
                try {
                    if (inputStream.read(inputByteBuffer.array()) > 0) {
                        inputByteBuffer.position(0);
                        int inputOpcode = inputByteBuffer.getShort();
                        int size = inputByteBuffer.getInt();
                        System.out.println(inputOpcode + "\t" + size);
                        inputByteBuffer.clear();
                    }
                } catch (IOException ex) {
                    logger.debug(ex.getMessage());
                }
            }
        }
    }
    
}