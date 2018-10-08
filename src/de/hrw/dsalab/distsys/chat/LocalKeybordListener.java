package de.hrw.dsalab.distsys.chat;

import javax.swing.*;
import java.util.ArrayList;

public class LocalKeybordListener implements InputListener {
    private JTextArea textArea;
    private String nick;
    private String msg;


    public LocalKeybordListener(JTextArea textArea, String nick) {
        this.textArea = textArea;
        this.nick = nick;
    }

    @Override
    public void inputReceived(String str) {
        textArea.append("<" + nick + "> " + str + System.getProperty("line.separator"));
    }
}
