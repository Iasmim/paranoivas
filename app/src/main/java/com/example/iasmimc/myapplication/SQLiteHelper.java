package com.example.iasmimc.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iasmim.c on 2/13/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper{
    private String[] scriptSQLCreate;
    private String[] scriptSQLDelete;

    SQLiteHelper(Context ctx, String NOME_BANCO, int versao, String[] scriptSQLCreate, String[] scriptSQLDelete)
    {
        super(ctx, NOME_BANCO, null, versao);
        this.scriptSQLCreate = scriptSQLCreate;
        this.scriptSQLDelete = scriptSQLDelete;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        int qtde = scriptSQLCreate.length;

        for (int i = 0; i < qtde; i++) {

            String sql = scriptSQLCreate[i];
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        int qtde = scriptSQLDelete.length;

        for (int i = 0; i < qtde; i++) {

            String sql = scriptSQLDelete[i];
            db.execSQL(sql);
        }

        onCreate(db);
    }
}



