/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author user
 */
public class Server {

    private final static int PORT = 53152;
    private ServerSocket server;

    public void open() throws IOException {
        if (this.server == null) {
            return;
        }

        this.server = new ServerSocket(PORT);

        new Thread(new Runnable() {
            @Override
            public void run() {
                
            }
        }).start();
    }

    public void close() throws IOException {
        this.server.close();
    }

}
