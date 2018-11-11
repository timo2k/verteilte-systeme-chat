package de.hrw.dsalab.distsys.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private InputListener inputListener;
	private NetworkListener networkListener;
	private String nick;
	private String hostname = "penguin.omega.example.org";
	private PrintWriter out;
	private Scanner in;

	public Chat() {
		JPanel mainPanel;
		
		setTitle("Chat Tool v0.1");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		nick = retrieveNickName();
		mainPanel = setupChatView();
		getContentPane().add(mainPanel);
		getContentPane().getParent().invalidate();
		getContentPane().validate();

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
	
	private JPanel setupChatView() {
		JPanel panel = new JPanel();
		JPanel southPanel = new JPanel();
		JTextArea textArea = new JTextArea();
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
		
		panel.setLayout(new BorderLayout());
		panel.add(textArea, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		
		// this is just an example, please modify for your listeners accordingly...
		networkListener = new IrcNetworkListener();
		inputListener = new IrcInputListener(textArea, nick);
		
		return panel;
	}
	
	private String retrieveNickName() {
		return (String)JOptionPane.showInputDialog(this, "Enter your nickname please:", "Enter nickname", JOptionPane.QUESTION_MESSAGE);
	}

	private void write(String command, String message) {
		String fullMessage = command + " " + message;
		System.out.println(">>> " + fullMessage);
		this.out.print(fullMessage + "\r\n");
		this.out.flush();
	}

	public static void main(String[] args) {
		new Chat();

	}

}
