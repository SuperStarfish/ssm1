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

public abstract class Host {

    protected DatagramSocket cDatagramSocket;
    protected InetAddress cOtherClient;

    byte[] incomingData = new byte[1024];

    protected Socket cSocket;
    protected ObjectInputStream cInputStream;
    protected ObjectOutputStream cOutputStream;

    protected boolean isAlive = true;

    protected long previousTime = 0l;

    public Host() {}

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

    public void sendUDP(final Serializable object) {
        if(isAlive && System.currentTimeMillis() - previousTime > 33) {
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

    public void receiveUDP(final MessageHandler handler, final boolean continuous) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    byte[] data = new byte[incomingData.length];

                    try {
                        DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                        cDatagramSocket.receive(incomingPacket);
                        data = incomingPacket.getData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                         ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)){

                        final Object object = objectInputStream.readObject();
                        handler.handleMessage(object);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (continuous && isAlive);
            }
        });
        thread.setName("Incoming UDP Messages Thread");
        thread.start();
    }

    protected abstract Socket createSocket();

    public abstract boolean isHost();

    public void sendTCP(final Serializable object) {
        if(isAlive && System.currentTimeMillis() - previousTime > 33) {
            try {
                cOutputStream.writeUnshared(object);
                cOutputStream.flush();
            } catch (SocketException e) {
                e.printStackTrace();
                isAlive = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
                        isAlive = false;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (continuous && isAlive);
            }
        });
        thread.setName("Incoming TCP Messages Thread");
        thread.start();
    }

    public void dispose() {
        isAlive = false;
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
