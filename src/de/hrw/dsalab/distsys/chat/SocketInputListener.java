package de.hrw.dsalab.distsys.chat;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketInputListener implements InputListener {

    private JTextArea textArea;
    private String nick;

	public SocketInputListener(JTextArea textArea, String nick) {
            this.textArea = textArea;
            this.nick = nick;
        }

    @Override
    public void inputReceived(String str) {
        textArea.append("<" + nick + "> " + str + System.getProperty("line.separator"));

    }
}
