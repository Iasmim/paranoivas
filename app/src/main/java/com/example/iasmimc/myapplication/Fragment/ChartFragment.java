package com.example.iasmimc.myapplication.Fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.iasmimc.myapplication.Class.Convidados;
import com.example.iasmimc.myapplication.Adapters.LegendaConvidados;
import com.example.iasmimc.myapplication.Class.Debito;
import com.example.iasmimc.myapplication.Class.Legendas;
import com.example.iasmimc.myapplication.PieChart;
import com.example.iasmimc.myapplication.PieDetailsItem;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;
import com.example.iasmimc.myapplication.RepositorioScript;

import java.util.ArrayList;
import java.util.List;


public class ChartFragment extends Fragment {
    Repositorio repositorio;
    ListView listView;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ChartFragment newInstance(int section_number) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, section_number);

        fragment.setArguments(args);
        return fragment;
    }

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_chart, container, false);


        repositorio = new RepositorioScript(v.getContext());

        List<Convidados> convidados = new ArrayList<>();
        convidados = repositorio.ListarConvidados();


        List<Debito> debitos = new ArrayList<>();
        debitos = repositorio.ListarDebito();

        int confirmados = 0;
        int nconfirmados = 0;
        int nsabem = 0;
        int danoiva = 0;
        int donoivo = 0;

        int dividaspagas = 0;
        int dividaspendentes = 0;

        if(convidados != null) {

            for (int i = 0; i < convidados.size(); i++) {

                if (convidados.get(i).isConfirmado() == 0)
                    confirmados++;
                else if (convidados.get(i).isConfirmado() == 1)
                    nconfirmados++;
                else
                    nsabem++;

                if (convidados.get(i).getTipo() == 0)
                    danoiva++;
                else
                    donoivo++;
            }
        }


        if(debitos != null)
        {
            for (Debito debito : debitos) {
                if(debito.pagas == debito.parcelas)
                    dividaspagas++;
                else
                    dividaspendentes++;
            }

        }

        int total = confirmados + nconfirmados + nsabem;


        // Inflate the layout for this fragment


        listView = (ListView) v.findViewById(R.id.listchart);

        List<Legendas> lista = new ArrayList<Legendas>();

        Legendas l0 = new Legendas();
        l0.Color = 0;
        l0.Descricao =getResources().getString(R.string.title_convidados);
        l0.id = 99;
        l0.qtde = -1;

         Legendas l1 = new Legendas();
         l1.Color = 0;
         l1.Descricao =getResources().getString(R.string.title_confirmado);
         l1.qtde = confirmados;
         l1.id = 0;

        Legendas l2 = new Legendas();
        l2.Color =1;
        l2.Descricao = getResources().getString(R.string.title_naoconfirmado);
        l2.qtde = nconfirmados;
        l2.id = 2;

        Legendas l3 = new Legendas();
        l3.Color = 2;
        l3.Descricao = getResources().getString(R.string.title_naosabe);
        l3.qtde = nsabem;
        l3.id = 3;

        Legendas l4 = new Legendas();
        l4.Color = 3;
        l4.Descricao =  getResources().getString(R.string.title_total);
        l4.qtde = total;
        l4.id = 4;

        Legendas l5 = new Legendas();
        l5.Color = 5;
        l5.Descricao =  getResources().getString(R.string.title_dividas);
        l5.qtde = -1;
        l5.id = 5;

        Legendas l6 = new Legendas();
        l6.Color = 6;
        l6.Descricao =  getResources().getString(R.string.div_pagas);
        l6.qtde = dividaspagas;
        l6.id = 5;

        Legendas l7 = new Legendas();
        l7.Color = 7;
        l7.qtde = dividaspendentes;
        l7.Descricao =  getResources().getString(R.string.div_pendentes);
        l7.id = 5;

        lista.add(l0);
        lista.add(l1);
        lista.add(l2);
        lista.add(l3);
        lista.add(l4);
        lista.add(l5);
        lista.add(l6);
        lista.add(l7);
        listView.setAdapter(new LegendaConvidados(listView.getContext(), lista));

       return  v;
    }

}
