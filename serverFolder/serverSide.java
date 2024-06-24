import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class serverSide {

    public static void main(String[] args) throws Exception {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            System.out.println("Server has Started");

//             Socket clientSocket = serverSocket.accept();
//             System.out.println("Connected");

//             DataInputStream inClient = new DataInputStream(clientSocket.getInputStream());
//             DataOutputStream outClient = new DataOutputStream(clientSocket.getOutputStream());
//             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

//             // Thread for reading from client
//             Thread readThread = new Thread(() -> {
//                 try {
//                     String readString = "";
//                     while (!readString.equals("/stop")) {
//                         readString = inClient.readUTF();
//                         System.out.println("Client -> " + readString);
//                     }
//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }
//             });
//             readThread.start();

//             // Thread for writing to client
//             Thread writeThread = new Thread(() -> {
//                 try {
//                     String writeString = "";
//                     while (!writeString.equals("/stop")) {
//                         writeString = br.readLine();
//                         System.out.println("Server -> " + writeString);
//                         outClient.writeUTF(writeString);
//                         outClient.flush();
//                     }
//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }
//             });
//             writeThread.start();

//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//     }

// }]
while (true) {
    Socket clientSocket = serverSocket.accept();
    System.out.println("Connected to a client");

    // Create a new thread for each connected client
    Thread clientThread = new Thread(() -> handleClient(clientSocket));
    clientThread.start();
}
} catch (IOException e) {
e.printStackTrace();
}
}

private static void handleClient(Socket clientSocket) {
try {
DataInputStream inClient = new DataInputStream(clientSocket.getInputStream());
DataOutputStream outClient = new DataOutputStream(clientSocket.getOutputStream());

BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

String clientName = "Client" + clientSocket.getPort();
System.out.println(clientName + " has joined");

// Thread for reading from the client
Thread readThread = new Thread(() -> {
    try {
        String readString = "";
        while (!readString.equals("/stop")) {
            readString = inClient.readUTF();
            System.out.println(clientName + " -> " + readString);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
});
readThread.start();

// Thread for writing to the client
Thread writeThread = new Thread(() -> {
    try {
        String writeString = "";
        while (!writeString.equals("/stop")) {
            writeString = br.readLine();
            System.out.println("Server -> " + writeString);
            outClient.writeUTF(writeString);
            outClient.flush();
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