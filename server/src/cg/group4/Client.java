package cg.group4;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    protected static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    protected ObjectOutputStream cOutputStream;

    protected ObjectInputStream cInputStream;

    protected Socket cConnection;

    protected String cServerIP;

    protected int cServerPort;

    protected final int cDefaultPort = 56789;

    protected final String cDefaultIP = "10.0.0.6";

    protected final int cMaxPortNumber = 65535;

    protected boolean cKeepAlive = true;

    protected final String cIPPattern =
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    protected BufferedReader bufferedReader;

    public Client(){
        setIPAndPort();
    }

    public Client(boolean useDefaults) {
        if (useDefaults && validatePort(cDefaultPort) && validateIP(cDefaultIP)) {
            cServerPort = cDefaultPort;
            cServerIP = cDefaultIP;
        } else {
            setIPAndPort();
        }
    }

    protected void setIPAndPort(){
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            cServerIP = askForIP();
            cServerPort = askForPort();

            bufferedReader.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    protected String askForIP() throws IOException {
        String input;
        do {
            System.out.print("Please enter the server IP address: ");
            input = bufferedReader.readLine();
        } while (!validateIP(input));
        return input;
    }

    protected int askForPort() throws IOException {
        boolean isValid = false;
        int input = -1;
        do {
            System.out.print("Please enter the server port: ");
            try {
                input = Integer.parseInt(bufferedReader.readLine());
                isValid = true;
            } catch (NumberFormatException e){
                System.out.println("That is not an integer!");
            }
        } while(!isValid && !validatePort(input));
        return input;
    }

    public final void openConnection() {
        try {
            connectToServer();
            createStreams();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public final void closeConnection(){
        try {
            cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void connectToServer() throws IOException {
        System.out.println("Attempting to connect to server...");
        try {
            cConnection = new Socket(cServerIP, cServerPort);
            System.out.println("test");
        } catch (ConnectException connectException){
            System.out.println(connectException.getMessage());
            System.exit(1);
        }
    }

    protected final void createStreams() throws IOException {
        cOutputStream = new ObjectOutputStream(cConnection.getOutputStream());
        cOutputStream.flush();
        cInputStream = new ObjectInputStream(cConnection.getInputStream());
    }

    protected final void cleanUp() throws IOException {
        if (cOutputStream != null) {
            cOutputStream.close();
        }
        if (cInputStream != null) {
            cInputStream.close();
        }
        if (cInputStream != null) {
            cConnection.close();
        }
    }

    protected final void interactWithServer() {
        do {
            try {
                Message message = (Message) cInputStream.readObject();
                System.out.println(message.type);
                switch (message.type){
                    case REPLY:
                        System.out.println(message.body);
                        break;
                    case CLOSE:
                        cKeepAlive = false;
                        break;
                    default:
                        LOGGER.severe("Received a message and I don't know how to handle it!");
                        break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SocketException e){
                LOGGER.info("Lost connection with: " + cConnection.getInetAddress().getHostName());
                cKeepAlive = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(cKeepAlive);
    }

    public void sendMessage(Message message){
        if(message.type != null){
            try{
                cOutputStream.writeObject(message);
                cOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            System.out.println("No message type specified!");
        }
    }

    protected boolean validateIP(final String ip) {
        Pattern pattern = Pattern.compile(cIPPattern);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    protected boolean validatePort(final Integer port) {
        return port != null && port >= 0 && port <= cMaxPortNumber;
    }

    public static void main(String[] args) {
        Client client = new Client(true);
        client.openConnection();
        client.sendMessage(new Message(Message.Type.GET, "Hallo server!"));
        while(true){
            client.interactWithServer();
        }
    }
}
