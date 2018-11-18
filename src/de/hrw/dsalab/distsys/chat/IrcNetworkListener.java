package de.hrw.dsalab.distsys.chat;

import javax.swing.*;
import java.net.Socket;

/**
 * To receive message from the server
 */
public class IrcNetworkListener implements NetworkListener {

    private JTextArea textArea;
    private String nick;
    private Socket socket;


    /**
     * The Networkworklistener Constructor
     * @param nick the Nickname String
     * @param socket the socket instance
     * @param textArea the textarea instance
     */
    public IrcNetworkListener(String nick, Socket socket, JTextArea textArea) {
        this.nick = nick;
        this.socket = socket;
        this.textArea = textArea;
    }

    @Override
    public void messageReceived(String msg) {
       this.textArea.append(msg + "\n");
    }
}
