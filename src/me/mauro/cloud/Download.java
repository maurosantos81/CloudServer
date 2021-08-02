/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

import java.io.File;
import java.net.Socket;
import me.mauro.cloud.pacotes.DownloadPacote;
import me.mauro.cloud.pacotes.Pacote;

/**
 *
 * @author user
 */
public class Download implements Comando {

    @Override
    public void action(Pacote pkt, Socket socket) {
        DownloadPacote pacote = (DownloadPacote) pkt;
        String path = Server.STORAGE_PATH + "\\" + pacote.getUser() + "\\" + pacote.getNome();
        File file = new File(path);
    }

}
