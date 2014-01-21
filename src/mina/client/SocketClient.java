package mina.client;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author caoxin
 */
public class SocketClient {

    private static String ip;
    private static int port;
    private static int opcode;

    public static void main(String... args) throws Exception {
        int length = args.length;
        if (length < 2) {
            System.out.println("use : {ip}, {port}, {opcode} if {ip} is null or {*} default '127.0.0.1'");
        }
        if (length == 2) {
            ip = "127.0.0.1";
            port = new Integer(args[0]);
            opcode = new Integer(args[1]);
        }
        if (length == 3) {
            ip = args[0];
            port = new Integer(args[1]);
            opcode = new Integer(args[2]);
        }
        Socket socket = new Socket(ip, port);
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
    }
}