/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import me.mauro.cloud.pacotes.ListarFilesPacote;
import me.mauro.cloud.pacotes.Pacote;

/**
 *
 * @author user
 */
public class ListFiles implements Comando {

    @Override
    public void action(Pacote pacote, Socket socket) {
        String path = Server.STORAGE_PATH + pacote.getUser().getNome() + "\\";
        List<String> files = Arrays.asList(new File(path).list());

        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(new ListarFilesPacote(pacote.getIdentifier(), pacote.getUser(), files));

            oos.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
