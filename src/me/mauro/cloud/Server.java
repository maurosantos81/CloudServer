/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.mauro.cloud.pacotes.Pacote;
import me.mauro.cloud.pacotes.UploadPacote;

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
                    try {
                        Socket socket = server.accept();
                        InputStream is = socket.getInputStream();
                        ObjectInputStream ois = new ObjectInputStream(is);
                        Pacote pacote = (Pacote) ois.readObject();
                        Comandos comando = Comandos.values()[pacote.getComando()];

                        System.out.println("[" + socket.getRemoteSocketAddress() + "] Conexão estabelecida para " + comando);
                        new Thread(() -> {
                            System.out.println("[" + socket.getRemoteSocketAddress() + "] Processando...");
                            comando.getController().action(pacote, socket, ois);
                            System.out.println("[" + socket.getRemoteSocketAddress() + "] Terminado!");

                            try {
                                ois.close();
                                socket.close();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }).start();

                    } catch (IOException | RuntimeException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }).start();
    }

    private List<Pacote> receivePackets(Socket socket) {
        try {
            List<Pacote> result = new LinkedList<>();
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Pacote pacote = (Pacote) ois.readObject();
            result.add(pacote);
            if (pacote instanceof UploadPacote) {
                while (((UploadPacote) pacote).haveMoreFragments()) {
                    pacote = (UploadPacote) ois.readObject();
                    result.add(pacote);
                }
            }
            ois.close();
            is.close();

            return result;

        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void close() throws IOException {
        this.server.close();
    }

}
