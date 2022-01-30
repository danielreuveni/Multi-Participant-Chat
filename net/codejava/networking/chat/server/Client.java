package net.codejava.networking.chat.server;
import java.util.*;
import java.net.*;
import java.io.*;

public class Client {
    private int port;
    private String host;
    private String user;

    public Client(String host) {
        this.port = 5877;
        this.host = host;
    }

    public String getUserName() {
        return this.user;
    }

    public void setUserName(String name) {
        this.user = name;
    }

    public void executeClient() {
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Welcome to the chat! :)");
            new Write(socket, this).start();
            new Read(socket, this).start();
 
        } catch (UnknownHostException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
 
    }
    public static void main(String[] args) {
 
        Client client = new Client("localhost");
        client.executeClient();
    }
}
