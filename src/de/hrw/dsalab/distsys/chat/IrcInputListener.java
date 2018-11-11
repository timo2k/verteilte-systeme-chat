package de.hrw.dsalab.distsys.chat;

import javax.swing.JTextArea;

public class IrcInputListener implements InputListener {

	private JTextArea textArea;
	private String nick;
	
	public IrcInputListener(JTextArea textArea, String nick) {
		this.textArea = textArea;
		this.nick = nick;
	}
	
	@Override
	public void inputReceived(String str) {
		textArea.append("<" + nick + "> " + str + System.getProperty("line.separator"));
	}

}
