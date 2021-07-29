/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mauro.cloud.server;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class Pacote implements Serializable {

    private int identifier;
    private int fragment;
    private int fragmentOffset;

    private byte[] fileBytes;
    private String name;
    private Comando comando;

    public Comando getComando() {
        return comando;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getFragment() {
        return fragment;
    }

    public String getName() {
        return name;
    }

    public int getFragmentOffset() {
        return fragmentOffset;
    }

}
