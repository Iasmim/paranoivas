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
 * Created by iasmim.c on 3/10/2015.
 */
public class FinanceiroAdapter extends BaseAdapter{
    private Context context;
    private List<Debito> lista;


    public FinanceiroAdapter(Context ctx, List<Debito> lista)
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
        Debito c = lista.get(position);
        return c.id;
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Debito c = lista.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.financeiro, null);

        TextView name = (TextView) view.findViewById(R.id.nomeDebito);

        ImageView img = (ImageView) view.findViewById(R.id.status);

        TextView qt = (TextView) view.findViewById(R.id.qtde);

        TextView vl = (TextView) view.findViewById(R.id.valor);


        name.setText(c.nome);
        img.setImageResource(R.drawable.ic_action_cancel);
        qt.setText(""+c.parcelas+"");
        vl.setText(""+c.valor+"");


        return  view;
    }
}
