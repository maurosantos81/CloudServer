/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud;

/**
 *
 * @author user
 */
public enum Comandos {

    UPLOAD(new Upload()),
    DOWNLOAD(new Download()),
    LIST_FILES(new ListFiles());

    private final Comando controller;

    private Comandos(Comando controller) {
        this.controller = controller;
    }

    public Comando getController() {
        return controller;
    }
}
