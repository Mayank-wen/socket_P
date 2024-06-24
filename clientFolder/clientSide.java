import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class clientSide
{

    public static void main(String[] args) throws Exception
    {
        System.out.println("--- Client ---");
        try {
            Socket socket = new Socket("localhost", 6000);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream clientOutput = new DataOutputStream(socket.getOutputStream());
            DataInputStream clientInput = new DataInputStream(socket.getInputStream());

            
            Thread readThread = new Thread(() -> {
                try {
                    String str2 = "";
                    while (!str2.equals("/stop")) {
                        str2 = clientInput.readUTF();
                        System.out.println("Server -> " + str2);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();

            // Thread for writing to server
            Thread writeThread = new Thread(() -> {
                try {
                    String str = "";
                    while (!str.equals("/stop")) {
                        str = br.readLine();
                        System.out.println("Client -> " + str);
                        clientOutput.writeUTF(str);
                        clientOutput.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writeThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
