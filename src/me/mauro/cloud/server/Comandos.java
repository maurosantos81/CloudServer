/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author user
 */
public enum Comandos {

    DOWNLOAD(new Download());

    private final Comando controller;

    private Comandos(Comando controller) {
        this.controller = controller;
    }

    public Comando getController() {
        return controller;
    }

    public void executar(InputStream is) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(is);

        Pacote pacote;
        try {
            pacote = (Pacote) ois.readObject();
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } finally {
            ois.close();
        }

        pacote.getComando().action(pacote);
    }
}
