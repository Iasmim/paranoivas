package com.example.iasmimc.myapplication.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iasmimc.myapplication.Class.Legendas;
import com.example.iasmimc.myapplication.R;

import java.util.List;

/**
 * Created by iasmim.c on 3/9/2015.
 */
public class  LegendaConvidados extends BaseAdapter {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.legenda, null);
        return getLegenda(position, view);
    }




    private View getLegenda(int position, View view) {
        Legendas c = lista.get(position);

        ImageView name = (ImageView) view.findViewById(R.id.retangulo);
        if(c.Color  == 0)
           name.setImageResource(R.drawable.gift);
        else if(c.Color  == 1)
            name.setImageResource(R.drawable.gift);
        else if(c.Color  == 2)
            name.setImageResource(R.drawable.gift);
        else if(c.Color  == 3)
            name.setImageResource(R.drawable.gift);

        TextView qtde = (TextView) view.findViewById(R.id.textlegenda);
        qtde.setText(c.Descricao);

        TextView legenda = (TextView) view.findViewById(R.id.status_legenda);
        if(c.qtde != -1)
           legenda.setText(c.qtde + "");

        if (position == 5 || position == 0)
            view.setBackgroundColor(Color.parseColor("#F48FB1"));


        return  view;
    }

}
