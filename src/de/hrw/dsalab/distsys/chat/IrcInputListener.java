package de.hrw.dsalab.distsys.chat;

import javax.swing.JTextArea;

/**
 * To send messages to the server
 */
public class IrcInputListener implements InputListener {

	private JTextArea textArea;
	private String nick;
	private SocketThread socketThread;

	/**
	 * The Constuct0r
	 * @param textArea textarea instance
	 * @param nick the nickname string
	 * @param socketThread the socketthread instance
	 */
	public IrcInputListener(JTextArea textArea, String nick, SocketThread socketThread) {
		this.textArea = textArea;
		this.nick = nick;
		this.socketThread = socketThread;
	}
	
	@Override
	public void inputReceived(String str) {
		textArea.append("<" + nick + "> " + str + System.getProperty("line.separator"));
		socketThread.addNextMessage("PRIVMSG #pimmler " + ":" + str);
	}

}
