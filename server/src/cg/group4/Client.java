package cg.group4;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    protected ObjectOutputStream cOutputStream;

    protected ObjectInputStream cInputStream;

    protected Socket cConnection;

    protected String cServerIP;

    protected int cServerPort;

    public Client(){
        setIPAndPort();
    }

    public Client(boolean useDefault){

    }

    protected void setIPAndPort(){
        try {
            cServerIP = null;
            cServerPort = -1;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (cServerIP == null) {
                System.out.print("Enter an IP to connect to: ");
                String input = br.readLine();
                if(validateIP(input)){
                   cServerIP = input;
                } else{
                    System.out.print("Invalid IP given, please try again.");
                }
            }
            while (cServerPort == -1){
                System.out.println("Enter a port to connect to: ");
                try{
                    int port = Integer.parseInt(br.readLine());
                    if(port >= 0 && port <= 65535){
                        cServerPort = port;
                    } else{
                        System.out.println("Port should be between 0 and 65535");
                    }
                } catch (NumberFormatException nfe){
                    System.out.println("That is not an integer! Please try again.");
                }
            }
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public final void startRunning() {
        try {
            connectToServer();
            if(cConnection != null) {
                createStreams();
                whileInteracting();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                cleanUp();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    protected void connectToServer() throws IOException {
        System.out.println("Attempting to connect to server...");
        try {
            cConnection = new Socket(cServerIP, cServerPort);
        } catch (ConnectException connectException){
            System.out.println(connectException.getMessage());
        }

        if (cConnection != null) {
            System.out.println("Connected to: " + cConnection.getInetAddress().getHostName());
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

    protected final void whileInteracting() throws IOException {
        String message = "";
        do {
            try {
                message = (String) cInputStream.readObject();
                if(!message.equals("")){
                    System.out.println(message);
                }
            } catch (ClassNotFoundException cnfException) {
                System.out.println("Don't know what I received");
            }
        } while (!message.equals("STOP"));
    }

    private static final String PATTERN =
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public static boolean validateIP(final String ip){
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static void main(String[] args){
        Client client = new Client();
        client.startRunning();
    }
}
