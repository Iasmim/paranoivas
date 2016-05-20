package com.example.iasmimc.myapplication.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iasmimc.myapplication.Class.Debito;
import com.example.iasmimc.myapplication.R;

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

        TextView qt = (TextView) view.findViewById(R.id.qtde);

        TextView vl = (TextView) view.findViewById(R.id.valor);

        TextView pg = (TextView) view.findViewById(R.id.pagas);

        TextView tpg = (TextView) view.findViewById(R.id.totalpagas);

        TextView id = (TextView) view.findViewById(R.id.id_parcela);


        if(c.pagas == c.parcelas)
            vl.setTextColor(ColorStateList.valueOf(Color.BLUE));
        else
            vl.setTextColor(ColorStateList.valueOf(Color.RED));

        name.setText(c.nome);
        qt.setText(""+c.parcelas+"");
        vl.setText(""+c.valor+"");
        pg.setText(""+c.pagas+"");
        tpg.setText(""+c.parcelas+"");

        id.setText(""+c.id+"");

        return  view;
    }
}
