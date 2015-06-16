package cg.group4.game_logic.stroll.events.multiplayer_event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Host of a multi-player stroll.
 */
public class MultiplayerHost implements Host{
    protected Socket cSocket;
    protected ObjectInputStream cInputStream;
    protected ObjectOutputStream cOutputStream;
    protected long previousTime = 0l;

    public MultiplayerHost() {
        try {
            ServerSocketHints hints = new ServerSocketHints();
            hints.acceptTimeout = 0;
            ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 56151, hints);
            SocketHints socketHints = new SocketHints();
            hints.performancePrefLatency = 2;
            hints.performancePrefBandwidth = 1;
            cSocket = serverSocket.accept(socketHints);
            cOutputStream = new ObjectOutputStream(cSocket.getOutputStream());
            cOutputStream.flush();
            cInputStream = new ObjectInputStream(cSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(final Serializable object) {
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

    public void receive(final MessageHandler handler, final boolean continuous) {
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

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final Object incoming = cInputStream.readObject();
//                    Gdx.app.postRunnable(new Runnable() {
//                        @Override
//                        public void run() {
//                            messageHandler.handleMessage(incoming);
//                        }
//                    });
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    @Override
    public boolean isHost() {
        return true;
    }
}
