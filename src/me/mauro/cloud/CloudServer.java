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
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.open();
    }
}
