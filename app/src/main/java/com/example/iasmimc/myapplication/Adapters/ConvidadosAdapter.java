package com.example.iasmimc.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iasmimc.myapplication.Class.Convidados;
import com.example.iasmimc.myapplication.R;

import java.util.List;

/**
 * Created by iasmim.c on 2/13/2015.
 */
public class ConvidadosAdapter extends BaseAdapter {

    private Context context;
    private List<Convidados> lista;


    public ConvidadosAdapter(Context ctx, List<Convidados> lista)
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
        Convidados c = lista.get(position);
        return c.getId();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Convidados c = lista.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.convidado, null);

        TextView name = (TextView) view.findViewById(R.id.convidado);
        name.setText(c.getNome());

        ImageView img = (ImageView) view.findViewById(R.id.icon);

        ImageView cf = (ImageView) view.findViewById(R.id.confirma);

        TextView qtde = (TextView) view.findViewById(R.id.qtde);
        qtde.setText(String.format("%d",c.getQtde()));


        TextView id = (TextView) view.findViewById(R.id.id);
        id.setText(String.format("%d",c.getId()));

        String stp = "Noiva";
        img.setImageResource(R.drawable.noiva);
        int tp = c.getTipo();

        if(Integer.valueOf(tp).equals(1)) {
            stp = "Noivo";
            img.setImageResource(R.drawable.noivo);
        }


        if(c.isConfirmado() ==  (0))
            cf.setImageResource(R.drawable.ic_action_like);
        else if(c.isConfirmado() ==  (1))
            cf.setImageResource(R.drawable.ic_action_dontlike);
        else
            cf.setImageResource(R.drawable.ic_action_help);


        return  view;
    }


}
