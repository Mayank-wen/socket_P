import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Server {
    public static void main(String[] args) {
        Socket socket=null;
        InputStreamReader inputStreamReader=null;
        OutputStreamWriter outputStreamWriter=null;
        BufferedReader bufferedReader=null;
        BufferedWriter bufferedWriter=null;
        ServerSocket serverSocket=null;

        try{
        serverSocket=new ServerSocket(1234);
        while (true){
            try{
                socket=serverSocket.accept();

                inputStreamReader=new InputStreamReader(socket.getInputStream());
                outputStreamWriter= new OutputStreamWriter(socket.getOutputStream());
                bufferedReader=new BufferedReader(inputStreamReader);
                bufferedWriter =new BufferedWriter(outputStreamWriter);
                
                while(true){

                    String msgFromClient=bufferedReader.readLine();

                    System.out.println("Client 1" + msgFromClient);
                 
                    bufferedWriter.write("Message Received!");
                    System.out.println("Client2 "+msgFromClient);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if(msgFromClient.equalsIgnoreCase("bye"))
                    break;

                }
               socket.close();
               inputStreamReader.close();
               outputStreamWriter.close();
               bufferedReader.close();
               bufferedWriter.close();
               serverSocket.close(); 
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
        catch(IOException e){
            e.printStackTrace();
        }
       

    }
    
}
