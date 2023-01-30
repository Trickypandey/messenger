import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;;
class server{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public server(){
       try {
        server = new ServerSocket(7777);
        System.out.println("server is ready waiting for request");
        socket=server.accept();

        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out = new PrintWriter(socket.getOutputStream());

        startReading();
        stopWriting();

        } 
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void startReading() {
        // thread for reading data from client
        // can create threads like this also
        Runnable r1=()->{
            try {
                while(true){
                    String msg= br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("client has terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("CLIENT:-" +msg);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // System.out.println("connection lost");
            }
        };
        new Thread(r1).start();
    }
    private void stopWriting() {
        // thread for wrinting and sent to client
        Runnable r2=()->{
            try {
            while(true){
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine(); 
                    out.println(content);
                    out.flush();
                
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("");
        new server();
    }
}