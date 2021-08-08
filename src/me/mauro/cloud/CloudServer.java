/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.IOException;

/**
 *
 * @author user
 */
public class CloudServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server();
        server.open();

//        ServerSocket server = new ServerSocket(53152);
//        Socket socket = server.accept();
//        
//        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//
//        while (ois.available() > 0){
//            System.out.println(ois.readInt());
//        }
//
//        ois.close();
//        server.close();
    }
}
