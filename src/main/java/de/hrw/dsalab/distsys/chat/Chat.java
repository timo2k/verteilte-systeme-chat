package de.hrw.dsalab.distsys.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.net.Socket;

import javax.swing.*;

public class Chat extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private InputListener inputListener;
	private NetworkListener networkListener;
	private String nick;
	ConfigParser config;

	private Socket serverSocket;
	private SocketThread serverListener;

	private String server;
	private String hostname;
	private int port;
	private String channel;
	private String defaultLanguage;

	private JTextArea textArea;

	public Chat() {
		JPanel mainPanel;
		
		setTitle("Chat Tool v0.1");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		nick = retrieveNickName();


		this.config = new ConfigParser();
		this.server = config.getServer();
		this.hostname = config.getHostname();
		this.port = config.getPort();
		this.channel = config.getChannel();
		this.defaultLanguage = config.getDefaultLanguage();

		// Do Socket Business
		if ((serverSocket = this.retrieveSocket()) == null) {
			System.out.println("Kaputt!");
			System.exit(0);
		}
		try {
			this.serverListener = new SocketThread(this.nick, this.serverSocket, this.hostname, this.channel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mainPanel = setupChatView();
		getContentPane().add(mainPanel);
		getContentPane().getParent().invalidate();
		getContentPane().validate();

		Thread socketThread = new Thread(serverListener);
		socketThread.start();
	}

	/**
	 * Setup for the UI
	 * @return JPanel
	 */
	private JPanel setupChatView() {
		JPanel panel = new JPanel();
		JPanel southPanel = new JPanel();
		this.textArea = new JTextArea();
		final JTextField textField = new JTextField();
		JButton sendButton = new JButton("Send");
				
		textField.setColumns(60);
		
		sendButton.addActionListener(e -> {
			inputListener.inputReceived(textField.getText());
			textField.setText("");
		});
		
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setEditable(false);

		southPanel.setLayout(new FlowLayout());
		southPanel.add(textField);
		southPanel.add(sendButton);

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setAutoscrolls(true);

		panel.setLayout(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		
		// this is just an example, please modify for your listeners accordingly...
		inputListener = new IrcInputListener(textArea, nick, serverListener, this.channel);
		networkListener = new IrcNetworkListener(this.nick, this.serverSocket, this.textArea, this.defaultLanguage);

		// push the networklistener and textaera instance into the serverListener Thread befor it starts
		serverListener.setNetworkListener(networkListener);
		serverListener.setTextArea(textArea);
		
		return panel;
	}

	/**
	 * To setup the Nickname
	 * @return
	 */
	private String retrieveNickName() {
		return (String)JOptionPane.showInputDialog(this, "Enter your nickname please:", "Enter nickname", JOptionPane.QUESTION_MESSAGE);
	}

	//TODO Refactor Socket Stuff
	/**
	 * Returns a brand new Socket
	 * @return Socket
	 */
	private Socket retrieveSocket() {
		try {
			Socket socket = new Socket(this.server, this.port);
			return socket;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		new Chat();

	}

}
