package com.example.iasmimc.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.iasmimc.myapplication.Class.Config;
import com.example.iasmimc.myapplication.Class.Convidados;
import com.example.iasmimc.myapplication.Class.Debito;
import com.example.iasmimc.myapplication.Class.Fornecedor;

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

        if(conv.getId() != 0)
            deletarConvidados(conv.getId());

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

        int count = db.delete(NOME_TABELA_CONVIDADOS, where, whereArgs);
        return  count;
    }


    public int deletarForne(Long id)
    {
        String where = "_id =?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[]{_id};

        int count = db.delete(NOME_TABELA_FORNECEDORES, where, whereArgs);
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


    public List<Debito> ListarDebito()
    {
        Cursor c = db.query(NOME_DESPESAS, new String[]{"_id","nomedebito","parcelas","valortotal", "pagas"}, null, null, null, null, null, null);

        List<Debito> cv = new ArrayList<Debito>();

        if(c.moveToFirst())
        {

            do {
                Debito d = new Debito();
                d.nome = (c.getString(c.getColumnIndex("nomedebito")));
                d.parcelas = (c.getInt(c.getColumnIndex("parcelas")));
                d.valor = (c.getDouble(c.getColumnIndex("valortotal")));
                d.pagas = (c.getInt(c.getColumnIndex("pagas")));
                d.id = (c.getInt(c.getColumnIndex("_id")));
                cv.add(d);
            }
            while (c.moveToNext());
        }

        return  cv;


    }



    public  long inserirDebito(Debito d)
    {
        ContentValues values = new ContentValues();
       if(d.id != 0)
           deletarDebito(d.id);

        values.put("nomedebito", d.nome);
        values.put("parcelas", d.parcelas);
        values.put("valortotal",d.valor);
        values.put("pagas",d.pagas);
       // values.put("id",d.id);
        long id = db.insert(NOME_DESPESAS, "", values);
        return  id;
    }

    public int deletarDebito(Long id)
    {
        String where = "_id =?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[]{_id};

        int count = db.delete(NOME_DESPESAS, where , whereArgs);
        return  count;
    }


    public void fechar() {
        if (db != null) {
            db.close();
        }
    }
}
