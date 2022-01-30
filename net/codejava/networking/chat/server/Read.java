package net.codejava.networking.chat.server;
import java.net.*;
import java.io.*;
import java.util.Calendar;


public class Read extends Thread{
    private BufferedReader br;
    private Socket socket;
    private Client client;
 
    public Read(Socket socket, Client client) {
        this.client = client;
        this.socket = socket;
 
        try {
            //"put" the message in buffred reader
            InputStream input = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
        while (true) {
            try {
                String response = br.readLine();
                System.out.println("\n" + response);
                Calendar c = Calendar.getInstance();
                String time = c.get(Calendar.HOUR_OF_DAY)+ ":"+ c.get(Calendar.MINUTE);
                //add the time to the message and print the message
                if (client.getUserName() != null) {
                    System.out.print("(" + time + ")" + " [" + client.getUserName() + "]: ");
                }
            } catch (IOException ex) {
                if (!socket.isClosed()) {
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
                }
                
                break;
            }
        }
    }
}