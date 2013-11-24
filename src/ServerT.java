import java.net.*;
import java.io.*;

public class ServerT {
    
    static final int PORT = 5009;
   
    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("[Server] M-am conectat la portul 2002");
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            
            System.out.println("[Server] S-a conectat un user");
            // new thread for a client
            new ServerThread(socket).start();
        }
    }
}
    