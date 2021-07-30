/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author user
 */
public class Upload implements Comando {

    @Override
    public void action(Pacote pacote) {
        try {
            FileOutputStream fos;

//            if (pacote.getFragment() == 0) {
//                fos = new FileOutputStream(new File(pacote.getName() + pacote.hashCode()), true);
//            } else {
                fos = new FileOutputStream(new File(getName(pacote)), true);
//            }

            while (pacote.getFragment() != 0 && pacote.getFragmentOffset() < new File(Server.STORAGE_PATH).length()) {
                Thread.sleep(300);
            }

            fos.write(pacote.getFileBytes());
            fos.close();
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getName(Pacote pacote) {
        String name = Server.STORAGE_PATH + pacote.getName();
        int i = 1;
        while (new File(name).exists()) {
            if (name.contains(String.format("(%d)", i))) {
                name = name.replace(String.format("(%d)", i), String.format("(%d)", ++i));
            } else {
                String[] split = name.split(".");
                String extension = split[split.length - 1];
                name = name.substring(0, name.length() - extension.length() -1) + String.format("(%d)", i) + "." + extension;
            }
        }
        return name;
    }
}
