package cg.group4.game_logic.stroll.events.multiplayer_event;

import com.badlogic.gdx.Gdx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    protected long previousTime = 0l;

    public Host() {}

    public void connect() {
        cSocket = createSocket();
        cOtherClient = cSocket.getInetAddress();
        try {
            cDatagramSocket = new DatagramSocket(55555);
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
        if(System.currentTimeMillis() - previousTime > 33) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(object);
                byte[] data = outputStream.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, cOtherClient, 55555);
                cDatagramSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void receiveUDP(final MessageHandler handler, final boolean continuous) {
        new Thread(new Runnable() {
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
                } while (continuous);
            }
        }).start();
    }

    protected abstract Socket createSocket();

    public abstract boolean isHost();

    public void sendTCP(final Serializable object) {
        if(System.currentTimeMillis() - previousTime > 33) {
            try {
                cOutputStream.reset();
                cOutputStream.writeObject(object);
                cOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void receiveTCP(final MessageHandler handler, final boolean continuous) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        final Object object = cInputStream.readObject();
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                handler.handleMessage(object);
                            }
                        });
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (continuous);
            }
        }).start();
    }


}
