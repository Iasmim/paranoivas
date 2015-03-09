package com.example.iasmimc.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iasmim.c on 2/12/2015.
 */
public class Repositorio {

    private static final String NOME_BANCO = "dados";
    public static final String NOME_TABELA_CONVIDADOS = "convidados";
    public  static final String NOME_TABELA_FORNECEDORES = "forne";
    public  static final String NOME_DESPESAS = "despesas";
    public  static final String NOME_PARCELADAS = "parc";
    public  static final String NOME_CONF = "config";
    protected SQLiteDatabase db;

    public  Repositorio()
    {

    }

    public Repositorio(Context ctx)
    {
        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    public long salvarConvidado(Convidados conv)
    {
        long id = conv.getId();
        if(id != 0)
            id = inserirConvidado(conv);

        return  id;
    }


    public  long inserirConvidado(Convidados conv)
    {
        ContentValues values = new ContentValues();
       // values.put("id", conv.getId());
        values.put("nome", conv.getNome());
        values.put("qtde", conv.getQtde());
        values.put("tipo", conv.getTipo());
        values.put("confirmado", conv.isConfirmado());

        long id = db.insert(NOME_TABELA_CONVIDADOS, "", values);
        return  id;
    }


     public  long atulizaConfig(Config cf)
    {
        int count = db.delete(NOME_CONF, null , null);
        ContentValues values = new ContentValues();
        // values.put("id", conv.getId());
        values.put("noiva", cf.getNoiva());
        values.put("noivo", cf.getNoivo());
        values.put("dia", cf.getDia());
        values.put("mes", cf.getMes());
        values.put("ano", cf.getAno());
        long id = db.insert(NOME_CONF, "", values);
        return  id;
    }

    public int deletarConvidados(Long id)
    {
        String where = "_id =?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[]{_id};

        int count = db.delete(NOME_TABELA_CONVIDADOS, where , whereArgs);
        return  count;
    }


    public long salvarForne(Fornecedor f)
    {
        long id = f.getID();
        if(id != 0)
            id = inserirForne(f);

        return  id;
    }
    public  long inserirForne(Fornecedor f)
    {
        ContentValues values = new ContentValues();
        values.put("id", f.getID());
        values.put("nome", f.getNome());
        values.put("custo", f.getCusto());
        values.put("tipo", f.getTipo());

        long id = db.insert(NOME_TABELA_FORNECEDORES, "", values);
        return  id;
    }

    public int deletarForne(Long id)
    {
        String where = "_id =?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[]{_id};

        int count = db.delete(NOME_TABELA_FORNECEDORES, where , whereArgs);
        return  count;
    }

    public List<Convidados> ListarConvidados()
    {
        Cursor c = db.query(NOME_TABELA_CONVIDADOS, new String[]{"_id","qtde","tipo","confirmado", "nome"}, null, null, null, null, null, null);

        List<Convidados> cv = new ArrayList<Convidados>();

        if(c.moveToFirst())
        {

            do {
                Convidados conv = new Convidados();
                conv.setNome(c.getString(c.getColumnIndex("nome")));
                conv.setTipo(c.getInt(c.getColumnIndex("tipo")));
                conv.setConfirmado(c.getInt(c.getColumnIndex("confirmado")));
                conv.setQtde(c.getInt(c.getColumnIndex("qtde")));
                conv.setId(c.getInt(c.getColumnIndex("_id")));
                cv.add(conv);
            }
                while (c.moveToNext());
            }

        return  cv;


    }

    public Config getConf()
    {
        Cursor c = db.query(NOME_CONF, new String[]{"_id","noiva","noivo","dia", "mes", "ano"}, null, null, null, null, null, null);

       Config cv = new Config();

        if(c.moveToFirst())
        {

            do {

                cv.setNoiva(c.getString(c.getColumnIndex("noiva")));
                cv.setNoivo(c.getString(c.getColumnIndex("noivo")));
                cv.setDia(c.getInt(c.getColumnIndex("dia")));
                cv.setMes(c.getInt(c.getColumnIndex("mes")));
                cv.setAno(c.getInt(c.getColumnIndex("ano")));

            }
            while (c.moveToNext());
        }

        return  cv;


    }


    public void fechar() {
        if (db != null) {
            db.close();
        }
    }
}
