/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author user
 */
public class Server {

    public final static String STORAGE_PATH = System.getProperty("user.home") + "\\Desktop\\MyCloud\\";

    private final static int PORT = 53152;
    private ServerSocket server;

    public Server() {
        File dir = new File(STORAGE_PATH);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
    }

    public void open() throws IOException {
        if (this.server != null) {
            return;
        }

        this.server = new ServerSocket(PORT);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Waiting for a connection...");
                    Socket socket;
                    try {
                        socket = server.accept();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                        Pacote pacote = (Pacote) ois.readObject();
                        Comandos comando = Comandos.values()[pacote.getComando()];
                        new Thread(() -> comando.getController().action(pacote)).start();

                        System.out.println("Conexão estabelecida com " + socket.getRemoteSocketAddress() + " para " + comando);
                    } catch (ClassNotFoundException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }).start();
    }

    public void close() throws IOException {
        this.server.close();
    }

}
