package de.hrw.dsalab.distsys.chat;

import javax.swing.JTextArea;

/**
 * To send messages to the server
 */
public class IrcInputListener implements InputListener {

	private JTextArea textArea;
	private String nick;
	private SocketThread socketThread;
	private String channel;
	private TranslationService translationService;

	/**
	 * The Constuct0r
	 * @param textArea textarea instance
	 * @param nick the nickname string
	 * @param socketThread the socketthread instance
	 */
	public IrcInputListener(JTextArea textArea, String nick, SocketThread socketThread, String channel) {
		this.textArea = textArea;
		this.nick = nick;
		this.socketThread = socketThread;
		this.channel = channel;
		this.translationService = new TranslationService();
	}

	
	@Override
	public void inputReceived(String str) {
		String textMsg = this.translationService.translateData(str, "en");
		textArea.append("<" + nick + "> " + textMsg + System.getProperty("line.separator"));
		socketThread.addNextMessage("PRIVMSG " + this.channel + " :" + textMsg);
	}

}
