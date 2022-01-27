
package net.codejava.networking.chat.server;

import java.util.*;
import java.net.*;
import java.io.*;


public class Server {
    
    //we use set in order not to allow duplication of users
    private Set<String> users = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void executeServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");
                UserThread newUser = new UserThread(this, socket);
                userThreads.add(newUser);
                newUser.run();
 
            }
        }
        catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addUser(String newUser) {
        users.add(newUser);
    }

    void sendMessageToAll(String message, UserThread self) {
        //send the message for all users excpet the sender
        for (UserThread user : userThreads) {
            if (user != self) {
                user.sendMessage(message);
            }
        }
    }

    public void deleteUser(String user, UserThread userThread) {
        boolean isRemoved = users.remove(user);
        if (isRemoved) {
            userThreads.remove(user);
            System.out.println(user + " left chat");
        }
    }

    public Set<String> getUsers() {
        return this.users;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isEmptyServer() {
        return !this.users.isEmpty();
    }


    public static void main(String[] args) {
        Server server = new Server(8989);
        server.executeServer();
    }

}