package de.hrw.dsalab.distsys.chat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ConfigParser {
    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder builder;

    private String server;
    private String hostname;
    private String port;
    private String channel;
    private String defaultLanguage;

    public ConfigParser() {
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("Config.xml"));
            NodeList nList = document.getElementsByTagName("chat");


            Node chat = nList.item(0);
            Element e = (Element)chat;
            this.server = e.getElementsByTagName("server").item(0).getTextContent().trim();
            this.hostname = e.getElementsByTagName("hostname").item(0).getTextContent().trim();
            this.port = e.getElementsByTagName("port").item(0).getTextContent().trim();
            this.channel = e.getElementsByTagName("channel").item(0).getTextContent().trim();
            this.defaultLanguage = e.getElementsByTagName("default-language").item(0).getTextContent().trim();


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHostname() {
        return hostname;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return Integer.parseInt(port);
    }

    public String getChannel() {
        return channel;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }
}
