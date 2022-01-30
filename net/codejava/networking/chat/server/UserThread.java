package net.codejava.networking.chat.server;
import java.util.*;
import java.net.*;
import java.io.*;

public class UserThread {
    private String user;
    private PrintWriter printWriter;
    private Server server;
    private Socket socket;

    public UserThread(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void sendMessage(String message) {
        printWriter.println(message);
    }


    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            //add the user to the chat and notify to all users
            String userName = br.readLine();
            server.addUser(userName);
            String serverMessage = "Welcome " + userName + "!";
            server.sendMessageToAll(serverMessage, this);
            String clientMessage;
            Calendar c = Calendar.getInstance();
            String time = c.get(Calendar.HOUR_OF_DAY)+ ":"+ c.get(Calendar.MINUTE);
            do {
                clientMessage = br.readLine();
                serverMessage = "(" + time + "): " + userName + ": " + clientMessage;
                server.sendMessageToAll(serverMessage, this);
 
            } while (!clientMessage.equals("bye"));
            //if user disconnect from the chat, we need to delete him from the server, close his socket and notify for all users
            server.deleteUser(userName, this);
            socket.close();
            serverMessage = userName + " left the chat.";
            server.sendMessageToAll(serverMessage, this);

        }
        catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


}

