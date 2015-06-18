package cg.group4.game_logic.stroll.events.multiplayer_event;

import com.badlogic.gdx.Gdx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * A host can either be a Client or Host.
 */
public abstract class Host {
    /**
     * DatagramSocket used for UDP messaging.
     */
    protected DatagramSocket cDatagramSocket;
    /**
     * The address of the other person.
     */
    protected InetAddress cOtherClient;
    /**
     * The default Byte Array size used for UDP.
     */
    protected final int cIJustTookThisAsDefaultBufferSize = 1024;
    /**
     * A buffer for the UDP data.
     */
    protected byte[] cIncomingData = new byte[cIJustTookThisAsDefaultBufferSize];
    /**
     * TCP connetion with the other client.
     */
    protected Socket cSocket;
    /**
     * ObjectInputStream for receiving Objects from the Socket.
     */
    protected ObjectInputStream cInputStream;
    /**
     * ObjectOutputStream for sending Objects to the Socket.
     */
    protected ObjectOutputStream cOutputStream;
    /**
     * Determines if the connection is still alive. If that is not the case this will terminate while loops.
     */
    protected boolean cIsAlive = true;
    /**
     * The last time a message is send, used to limit the number of messages send per second.
     */
    protected long cPreviousTime = 0L;
    /**
     * Limit the sending of data to 30 messages per second.
     */
    protected int cMessagePerSecond = 33;

    /**
     * Creates a new Host.
     */
    public Host() { }

    /**
     * Connects the host to the other party.
     */
    public void connect() {
        cSocket = createSocket();
        cOtherClient = cSocket.getInetAddress();
        try {
            cDatagramSocket = new DatagramSocket(cSocket.getLocalPort());
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            cOutputStream = new ObjectOutputStream(cSocket.getOutputStream());
            cOutputStream.flush();
            cInputStream = new ObjectInputStream(cSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends an Object using UDP.
     * @param object object to send.
     */
    public void sendUDP(final Serializable object) {
        if (cIsAlive && System.currentTimeMillis() - cPreviousTime > cMessagePerSecond) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(object);
                byte[] data = outputStream.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, cOtherClient, cSocket.getPort());
                cDatagramSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Receives an Object using UDP.
     * @param handler Action to perform with the message.
     * @param continuous Determines if this action will be performed all the time until cancelled.
     */
    public void receiveUDP(final MessageHandler handler, final boolean continuous) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    byte[] data = new byte[cIncomingData.length];

                    try {
                        DatagramPacket incomingPacket = new DatagramPacket(cIncomingData, cIncomingData.length);
                        cDatagramSocket.receive(incomingPacket);
                        data = incomingPacket.getData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                         ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

                        final Object object = objectInputStream.readObject();
                        handler.handleMessage(object);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (continuous && cIsAlive);
            }
        });
        thread.setName("Incoming UDP Messages Thread");
        thread.start();
    }

    /**
     * Returns the socket connection with the other client.
     * @return The socket connection.
     */
    protected abstract Socket createSocket();

    /**
     * If the current client is either a host or not.
     * @return Host or not.
     */
    public abstract boolean isHost();

    /**
     * Sends an Object using TCP.
     * @param object Object to send.
     */
    public void sendTCP(final Serializable object) {
        if (cIsAlive && System.currentTimeMillis() - cPreviousTime > cMessagePerSecond) {
            try {
                cOutputStream.writeUnshared(object);
                cOutputStream.flush();
            } catch (SocketException e) {
                e.printStackTrace();
                cIsAlive = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Receives an Object using TCP.
     * @param handler Action to perform with the message.
     * @param continuous Determines if this action will be performed all the time until cancelled.
     */
    public void receiveTCP(final MessageHandler handler, final boolean continuous) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        final Object object = cInputStream.readUnshared();
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                handler.handleMessage(object);
                            }
                        });
                    } catch (EOFException e) {
                        e.printStackTrace();
                        cIsAlive = false;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (continuous && cIsAlive);
            }
        });
        thread.setName("Incoming TCP Messages Thread");
        thread.start();
    }

    /**
     * Disposes the host connection.
     */
    public void dispose() {
        cIsAlive = false;
        cDatagramSocket.close();
        try {
            cSocket.close();
            cOutputStream.close();
            cInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
