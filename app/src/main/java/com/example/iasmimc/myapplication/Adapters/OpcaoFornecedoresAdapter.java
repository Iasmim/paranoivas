package com.example.iasmimc.myapplication.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iasmimc.myapplication.Class.Lugares;
import com.example.iasmimc.myapplication.R;

import java.util.List;

/**
 * Created by iasmim.c on 2/13/2015.
 */
public class OpcaoFornecedoresAdapter extends BaseAdapter {

    private Context context;
    private List<Lugares> lista;
    ImageView imageView;

    public OpcaoFornecedoresAdapter(Context ctx, List<Lugares> lista)
    {
        this.context = ctx;
        this.lista = lista;

    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public long getItemId(int position) {
        Lugares c = lista.get(position);
        return  c.id;
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Lugares c = lista.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_op_forne, null);

        TextView name = (TextView) view.findViewById(R.id.titulo);
        name.setText(c.name);

        imageView = (ImageView) view.findViewById(R.id.venue);
        imageView.setImageResource(R.drawable.ic_action_search);

        return  view;
    }


}
