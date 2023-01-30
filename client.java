import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// import java.awt.BoardLayout;


public class client extends JFrame {

    Socket socket;
    
    BufferedReader br;
    PrintWriter out;


    private JLabel heading = new JLabel("Client Area");
    private JTextArea messArea = new JTextArea();
    private JTextField mesinput = new JTextField();

    private Font font = new Font("Roboto",Font.PLAIN,20);
    private Font fontHeading = new Font("Roboto",Font.BOLD,20);

    public client(){
        try {
            // System.out.println("sending re to server");
            // socket = new Socket("192.168.1.8",7777);
            // System.out.println("connection done");
            // br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // out = new PrintWriter(socket.getOutputStream());
            createGUI();
            handleEvent();
            startReading();
            // stopWriting();


            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // GUI for client 

    private void createGUI(){
        this.setTitle("Client messager[END]");
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // all  component
        heading.setFont(fontHeading);
        messArea.setFont(font);
        mesinput.setFont(font);
        

        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        heading.setIcon(new ImageIcon("icon.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        

        this.setLayout(new BorderLayout());
        this.add(messArea,BorderLayout.CENTER);
        this.add(heading,BorderLayout.NORTH);
        this.add(mesinput,BorderLayout.SOUTH);
        


        this.setVisible(true);
    }
    private void handleEvent(){
        mesinput.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                // System.out.println("key released"+e.getKeyCode());
                if(e.getKeyCode()==10){
                    // System.out.println("you have pressed enter button");
                    String contenttosend = mesinput.getText();
                    messArea.append("me :-"+ contenttosend+"\n");
                    out.print(contenttosend);
                    out.flush();
                    mesinput.setText("");
                    mesinput.requestFocus();
                }
            }
            
        });
    }
    private void startReading() {
        // thread for reading data from client
        // can create threads like this also
        Runnable r1=()->{
            try {
                while(socket.isClosed()){
                    String msg= br.readLine();
                    if(msg.equals("exit")){
                        // System.out.println("SERVER has terminated the chat");
                        JOptionPane.showMessageDialog(this, "server terminated the chat");
                        mesinput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    // System.out.println("SERVER:-"+msg);
                    messArea.append("Server :-" + msg + "\n");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();

                System.out.println("connection to server has been lost");

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
        new client();
    }
}
