/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author user
 */
public class Download implements Comando {

    private final static String STORAGE_PATH = System.getProperty("user.home") + "/Desktop/";

    @Override
    public void action(Pacote pacote) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(getName(pacote)), true);
            while (pacote.getFragment() == 0 && pacote.getFragmentOffset() < new File(STORAGE_PATH).length()) {
                Thread.sleep(300);
            }
            
            fos.write(pacote.getFileBytes());
            fos.close();
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getName(Pacote pacote) {
        String name = STORAGE_PATH + pacote.getName();
        int i = 1;
        while (new File(name).exists()) {
            if (name.contains(String.format("(%d)", i))) {
                name = name.replace(String.format("(%d)", i), String.format("(%d)", ++i));
            } else {
                name += String.format("(%d)", i);
            }
        }
        return name;
    }
}
