package com.example.iasmimc.myapplication;

import android.content.Context;

/**
 * Created by iasmim.c on 2/12/2015.
 */
public class RepositorioScript extends Repositorio {

    private static final String SCRIPT_DATABASE_DELETE_CONVIDADOS = "DROP TABLE IF EXIST convidados";
    private static final String SCRIPT_DATABASE_DELETE_FORNE = "DROP TABLE IF EXIST forne";
    private static final String SCRIPT_DATABASE_DELETE_DESPESAS = "DROP TABLE IF EXIST despesas";
    private static final String SCRIPT_DATABASE_DELETE_PARC = "DROP TABLE IF EXIST parc";
    private static final String SCRIPT_DATABASE_DELETE_CONFIG = "DROP TABLE IF EXIST config";
/*
*     private  int ID;
    private String Tipo;
    private Double custo;
    private String nome;
* */
    private static final String[] SCRIPT_DATABASE_CREATE = new String[]
            {
                "create table convidados (_id integer primary key autoincrement, qtde  integer not null,tipo integer not null,confirmado integer not null,  nome text not null)"
               , "create table config (_id integer primary key autoincrement ,noiva text not null,noivo text not null, dia integer not null, mes integer not null, ano integer not null )"
               , "create table despesas (_id integer primary key autoincrement ,nomedebito text not null,parcelas integer not null, valortotal real not null)"
            };

    private static final String[] SCRIPT_DATABASE_DELETE = new String[]
            {
                   SCRIPT_DATABASE_DELETE_CONVIDADOS
                    ,SCRIPT_DATABASE_DELETE_DESPESAS
                    ,SCRIPT_DATABASE_DELETE_FORNE
                    ,SCRIPT_DATABASE_DELETE_PARC
                    ,SCRIPT_DATABASE_DELETE_CONFIG
            };

    private static final String NOME_BANCO = "dados";
    private static final int VERSAO_BANCO = 1;

    private SQLiteHelper dbHelper;
    public RepositorioScript(Context ctx)
    {
        dbHelper =new  SQLiteHelper(ctx, RepositorioScript.NOME_BANCO, RepositorioScript.VERSAO_BANCO, RepositorioScript.SCRIPT_DATABASE_CREATE, RepositorioScript.SCRIPT_DATABASE_DELETE);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void fechar() {
        super.fechar();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
