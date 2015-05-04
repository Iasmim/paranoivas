package com.example.iasmimc.myapplication.Class;

/**
 * Created by iasmim.c on 2/12/2015.
 */
public class Convidados {

    private long Id;
    private int Qtde;
    private String nome;
    private int Tipo;
    private int confirmado;

    public long getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQtde() {
        return Qtde;
    }

    public void setQtde(int qtde) {
        Qtde = qtde;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }

    public int isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(int confirmado) {
        this.confirmado = confirmado;
    }
}
