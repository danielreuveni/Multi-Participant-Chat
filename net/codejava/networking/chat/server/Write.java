package net.codejava.networking.chat.server;
import java.net.*;
import java.io.*;
import java.util.Calendar;

public class Write extends Thread{
    private PrintWriter pw;
    private Socket socket;
    private Client client;
 
    public Write(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
 
        try {
            OutputStream output = socket.getOutputStream();
            pw = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
        Console console = System.console();
        String userName = console.readLine("Enter your name: ");
        client.setUserName(userName);
        pw.println(userName);
 
        String text;
 
        do {
            Calendar c = Calendar.getInstance();
            String time = c.get(Calendar.HOUR_OF_DAY)+ ":"+ c.get(Calendar.MINUTE);
            text = console.readLine("(" + time + ")" + " [" + userName + "]: ");
            pw.println(text);
            // if the message is "bye" than the user disconnect from the chat
        } while (!text.equals("bye"));
 
        try {
            socket.close();
        } catch (IOException ex) {
 
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}