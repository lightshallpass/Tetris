import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class ClientThread extends  Thread {

    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null){
            Random random = new Random();
            try {
                OutputStream stream = socket.getOutputStream();
                InputStream outputStream = socket.getInputStream();
                while(true){
                    int result;
                    result = outputStream.read();
                    if (result == 0){
                        break;
                    }
                    stream.write(random.nextInt(7));
                    stream.flush();

                }
                stream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }
}
