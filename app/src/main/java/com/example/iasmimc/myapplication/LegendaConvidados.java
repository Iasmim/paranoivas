package com.example.iasmimc.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by iasmim.c on 3/9/2015.
 */
public class LegendaConvidados extends BaseAdapter {
    private Context context;
    private List<Legendas> lista;


    public  LegendaConvidados(Context context,List<Legendas> lista)
    {
        this.context = context;
        this.lista = lista;
    }
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        Legendas c = lista.get(position);
        return c.id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Legendas c = lista.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.legenda, null);

        TextView name = (TextView) view.findViewById(R.id.retangulo);
        name.setBackgroundColor(c.Color);

        TextView qtde = (TextView) view.findViewById(R.id.textlegenda);
        qtde.setText(c.Descricao);

        return  view;
    }
}
