/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author user
 */
public class Upload implements Comando {

    private static Map<Integer, File> fileNames = new HashMap();

    @Override
    public void action(Pacote pacote) {
        //criar o nome, e adiciona-lo ao hashmap
        //esse nome vai ser a referencia para os proximos fragmentos
        if (pacote.getFragment() == 0) {
            fileNames.put(pacote.getIdentifier(), new File(getName(pacote)));
        }

        try {
            //caso cheguem fragmentos na ordem errado.
            //ex: caso o 4º fragmento chegar primeiro que o 3º, ele vai ter que esperar
            if (pacote.getFragment() != 0) {

                while (pacote.getFragmentOffset() > fileNames.getOrDefault(pacote.getIdentifier(), new File("")).length()) {
                    Thread.sleep(300);
                }
            }

            //escrever no arquivo
            FileOutputStream fos = new FileOutputStream(fileNames.get(pacote.getIdentifier()), true);
            fos.write(pacote.getFileBytes());
            fos.close();
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        if (!pacote.haveMoreFragments()) {
            fileNames.remove(pacote.getIdentifier());
        }
    }

    private String getName(Pacote pacote) {
        String name = Server.STORAGE_PATH + pacote.getName();
        int i = 1;
        while (new File(name).exists()) {
            if (name.contains(String.format("(%d)", i))) {
                name = name.replace(String.format("(%d)", i), String.format("(%d)", ++i));
            } else {
                String[] split = name.split("\\.");
                String extension = split[split.length - 1];
                name = name.substring(0, name.length() - extension.length() - 1) + String.format("(%d)", i) + "." + extension;
            }
        }
        return name;
    }
}
