/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import me.mauro.cloud.pacotes.Pacote;

/**
 *
 * @author user
 */
public class Download implements Comando {

    @Override
    public void action(Pacote pacote, Socket socket, ObjectInputStream oos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
