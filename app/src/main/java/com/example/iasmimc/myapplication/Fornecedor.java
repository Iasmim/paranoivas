package com.example.iasmimc.myapplication;

/**
 * Created by iasmim.c on 2/12/2015.
 */
public class Fornecedor {
    private  int ID;
    private String Tipo;
    private Double custo;
    private String nome;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
