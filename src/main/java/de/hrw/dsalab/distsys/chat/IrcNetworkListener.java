package de.hrw.dsalab.distsys.chat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * To receive message from the server
 */
public class IrcNetworkListener implements NetworkListener {

    private JTextArea textArea;
    private String nick;
    private Socket socket;
    private TranslationService translationService;
    private String defaultLanguage;


    /**
     * The Networkworklistener Constructor
     * @param nick the Nickname String
     * @param socket the socket instance
     * @param textArea the textarea instance
     */
    public IrcNetworkListener(String nick, Socket socket, JTextArea textArea, String defaultLang) {
        this.nick = nick;
        this.socket = socket;
        this.textArea = textArea;
        this.translationService = new TranslationService();
        this.defaultLanguage = defaultLang;
    }


    @Override
    public void messageReceived(String msg) {
       String translatedMsg = this.translationService.translateData(msg, this.defaultLanguage);
        this.textArea.append(translatedMsg + "\n");
    }
}
