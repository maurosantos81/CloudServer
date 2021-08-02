/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import me.mauro.cloud.pacotes.Pacote;
import me.mauro.cloud.pacotes.UploadPacote;

/**
 *
 * @author user
 */
public class Upload implements Comando {

    private static Map<Integer, File> fileNames = new HashMap();

    @Override
    public void action(Pacote pkt, Socket socket) {
        UploadPacote pacote = (UploadPacote) pkt;
        //criar o nome, e adiciona-lo ao hashmap
        //esse nome vai ser a referencia para os proximos fragmentos
        if (pacote.getFragment() == 0) {
            fileNames.put(pacote.hashCode(), getFile(pacote));
        }

        try {
            //caso cheguem fragmentos na ordem errado.
            //ex: caso o 4º fragmento chegar primeiro que o 3º, ele vai ter que esperar
            if (pacote.getFragment() != 0) {
                while (pacote.getFragmentOffset() > fileNames.getOrDefault(pacote.hashCode(), new File("")).length()) {
                    Thread.sleep(300);
                }
            }

            File file = fileNames.get(pacote.hashCode());
            //criar o diretorio onde vai ser guardado o ficheiro
            createDir(file);

            //escrever no arquivo
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(pacote.getFileBytes());
            fos.close();
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        if (!pacote.haveMoreFragments()) {
            fileNames.remove(pacote.hashCode());
        }
    }

    private void createDir(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    //exemplo: caso ja existe um ficheiro com o nome "bbb.txt", a funçao gera o nome "bbb(1).txt"
    private File getFile(UploadPacote pacote) {
        String name = Server.STORAGE_PATH + pacote.getUser().getNome() + "\\" + pacote.getName();

        int i = 1;
        String[] split = name.split("\\.");
        String extension = split[split.length - 1];

        File file = new File(name);
        while (file.exists() && !file.isDirectory()) {
            file = new File(name.replace("." + extension,
                    String.format("(%d).%s", i++, extension)));
        }

        return file;
    }
}
