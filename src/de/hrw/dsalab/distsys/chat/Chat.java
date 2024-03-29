package de.hrw.dsalab.distsys.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private InputListener inputListener;
	private String nick;

	public Chat() {
		JPanel mainPanel;
		
		setTitle("Chat Tool v0.1");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		nick = retrieveNickName();
		mainPanel = setupChatView();
		getContentPane().add(mainPanel);
		getContentPane().getParent().invalidate();
		getContentPane().validate();
	}
	
	private JPanel setupChatView() {
		JPanel panel = new JPanel();
		JPanel southPanel = new JPanel();
		JTextArea textArea = new JTextArea();
		final JTextField textField = new JTextField();
		JButton sendButton = new JButton("Send");
				
		textField.setColumns(60);
		
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputListener.inputReceived(textField.getText());
				textField.setText("");
			}
			
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
		inputListener = new KeyboardListener(textArea, nick);
		
		return panel;
	}
	
	private String retrieveNickName() {
		return (String)JOptionPane.showInputDialog(this, "Enter your nickname please:", "Enter nickname", JOptionPane.QUESTION_MESSAGE);
	}

	public static void main(String[] args) {
		new Chat();
	}

}
