package cg.group4;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * The ServerThread interacts with the Client.
 */
public class ServerThread implements Callable<Void> {
    /**
     * Default java logging functionality.
     */
    private static final Logger LOGGER = Logger.getLogger(ServerThread.class.getName());

    /**
     * The connection with the Client.
     */
    protected Socket cConnection;

    /**
     * The ObjectOutputStream for outgoing messages.
     */
    protected ObjectOutputStream cOutputStream;

    /**
     * The ObjectInputStream for incoming messages.
     */
    protected ObjectInputStream cInputStream;

    /**
     * Determines if the connection needs to be kept alive.
     */
    protected boolean cKeepAlive = true;

    /**
     * Creates a new ServerThread for communication with the server and the client.
     *
     * @param connection The connection with the Client.
     */
    public ServerThread(final Socket connection) {
        cConnection = connection;
        LOGGER.info("Established a connection with: " + cConnection.getInetAddress().getHostName());
    }

    @Override
    public final Void call() throws Exception {
        try {
            createStreams();
            interactWithClient();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                cleanUp();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Creates the ObjectInput- and ObjectOutputStreams.
     *
     * @throws IOException IOException
     */
    protected final void createStreams() throws IOException {
        cOutputStream = new ObjectOutputStream(cConnection.getOutputStream());
        cOutputStream.flush();
        cInputStream = new ObjectInputStream(cConnection.getInputStream());
    }

    /**
     * Closes the ObjectInput- and ObjectOutputStream as well as the connection with the Client.
     *
     * @throws IOException IOException
     */
    protected final void cleanUp() throws IOException {
        String hostName = cConnection.getInetAddress().getHostName();
        cOutputStream.close();
        cInputStream.close();
        cConnection.close();
        LOGGER.info("Closed connection with: " + hostName);
    }

    /**
     * This method is used for incoming messages from the client.
     */
    protected final void interactWithClient() {
        do {
            try {
                Message message = (Message) cInputStream.readObject();
                switch (message.type){
                    case GET:
                        handleGetRequest(message);
                        break;
                    case PUT:
                        handlePutRequest(message);
                        break;
                    case POST:
                        handlePostRequest(message);
                        break;
                    case DELETE:
                        handleDeleteRequest(message);
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

    /**
     * Handles an incoming GET message.
     *
     * @param message Query made by the Client.
     */
    protected void handleGetRequest(Message message) {
        LOGGER.info("Received a GET message: " + message.body);
        Message reply = new Message();
        reply.type = Message.Type.REPLY;
        reply.body = "Get requests are not implemented yet.";
        sendReply(reply);
    }

    /**
     * Handles an incoming PUT message.
     *
     * @param message Query made by the Client.
     */
    protected void handlePutRequest(Message message) {
        Message reply = new Message();
        reply.type = Message.Type.REPLY;
        reply.body = "Put requests are not implemented yet.";
        sendReply(message);
    }

    /**
     * Handles an incoming POST message.
     *
     * @param message Query made by the Client.
     */
    protected void handlePostRequest(Message message) {
        Message reply = new Message();
        reply.type = Message.Type.REPLY;
        reply.body = "Post requests are not implemented yet.";
        sendReply(message);
    }

    /**
     * Handles an incoming DELETE message.
     *
     * @param message Query made by the Client.
     */
    protected void handleDeleteRequest(Message message) {
        Message reply = new Message();
        reply.type = Message.Type.REPLY;
        reply.body = "Delete requests are not implemented yet.";
        sendReply(message);
    }

    protected void sendReply(Message message){
        try {
            cOutputStream.writeObject(message);
            cOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
