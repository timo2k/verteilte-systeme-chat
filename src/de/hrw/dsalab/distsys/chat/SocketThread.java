package de.hrw.dsalab.distsys.chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketThread implements Runnable {

    private String hostname = "penguin.omega.example.org";
    private PrintWriter out;
    private Scanner in;
    private String nick;

    public SocketThread(String nick) {
        this.nick = nick;
    }

    private void write(String command, String message) {
        String fullMessage = command + " " + message;
        System.out.println(">>> " + fullMessage);
        this.out.print(fullMessage + "\r\n");
        this.out.flush();
    }


    @Override
    public void run() {
        try {
            Socket socket = new Socket("172.16.5.130", 6667);

            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new Scanner(socket.getInputStream());

            this.write("NICK", this.nick);
            this.write("USER", this.nick + " 0 * :" + this.nick);

            while(this.in.hasNext()) {
                String serverMessage = this.in.nextLine();
                System.out.println("<<< " + serverMessage);

                if(serverMessage.startsWith(":" + this.hostname + " 266")) {
                    write("JOIN", "#pimmler");
                    write("PRIVMSG #pimmler", ":Na Ihr Fotzen");
                }
            }

            in.close();
            out.close();
            socket.close();

            System.out.println("Done!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
