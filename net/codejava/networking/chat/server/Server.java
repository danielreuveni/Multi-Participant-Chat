
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
            System.out.println("Server is running...");
            while (true) {
                //connect new user to the server and make for him new user thread
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
        //send the message from the sender for all users
        for (UserThread user : userThreads) {
            if (user != self) {
                user.sendMessage(message);
            }
        }
    }

    public void deleteUser(String user, UserThread userThread) {
        //delete the user from the server 
        boolean isDeleted = users.remove(user);
        if (isDeleted) {
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
        Server server = new Server(5877);
        server.executeServer();
    }

}