import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTetris {



    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8090);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
           if (ss != null) {
               Socket socket = null;
               try {
                   socket = ss.accept();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               ClientThread clientThread = new ClientThread();
               clientThread.setSocket(socket);
               clientThread.start();
           }
       }
   }
}
