package de.hrw.dsalab.distsys.chat;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class SocketThread implements Runnable {

    private String hostname = "penguin.omega.example.org";
    private Socket socket;
    private String nick;
    private NetworkListener networkListener;
    private JTextArea textArea;
    private Scanner input;
    private PrintWriter output;


    /**
     * SocketThread Constructor
     * @param nick the Nickname
     * @param socket the Socket Instance
     * @throws IOException
     */
    public SocketThread(String nick, Socket socket) throws IOException {
        this.nick = nick;
        this.socket = socket;

        this.input = new Scanner(this.socket.getInputStream());
        this.output = new PrintWriter(socket.getOutputStream(), true);
    }


    @Override
    public void run() {
            System.out.println("GEHT=");
        try {
            this.write("NICK", this.nick);
            this.write("USER", this.nick + " 0 * :" + this.nick);

            while(this.input.hasNext()) {
                String serverMessage = this.input.nextLine();
                System.out.println("<<< " + serverMessage);
                //TODO: Filter and format the chat Message
                networkListener.messageReceived("<<< " + serverMessage);

                //TODO: Implement a PING Check
                if(serverMessage.startsWith(":" + this.hostname + " 266")) {
                    write("JOIN", "#pimmler");
                    write("PRIVMSG #pimmler", ":Na Ihr Fotzen");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Formatter to handle messages with the IRC commands
     * @param command IRC Command
     * @param message Server Message
     */
    private void write(String command, String message) {
        String fullMessage = command + " " + message;
        System.out.println(">>> " + fullMessage);
        this.output.print(fullMessage + "\r\n");
        this.output.flush();
    }

    /**
     * Send the next message to the server
     * @param msg message string
     */
    public void addNextMessage(String msg) {
        output.println(msg);
        output.flush();
    }

    /**
     * Sets the Networklistener
     * @param nl
     */
    public void setNetworkListener(NetworkListener nl) {
        this.networkListener = nl;
    }

    /**
     * Sets the TextArea
     * @param textArea
     */
    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
}
