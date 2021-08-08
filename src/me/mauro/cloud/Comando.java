/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.ObjectInputStream;
import java.net.Socket;
import me.mauro.cloud.pacotes.Pacote;

/**
 *
 * @author user
 */
public interface Comando {

    public void action(Pacote pacote, Socket socket, ObjectInputStream ois);
}
