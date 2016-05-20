package com.example.iasmimc.myapplication;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ChartFragment extends Fragment {
    Repositorio repositorio;
    View v;
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
        v =  inflater.inflate(R.layout.fragment_chart, container, false);
        return getView(v);
    }

    @Override
    public void onStart() {
        getView(v);
        super.onStart();
    }

    private View getView(View v) {
        repositorio = new RepositorioScript(v.getContext());

        List<Convidados> convidados = new ArrayList<>();
        //  convidados = repositorio.ListarConvidados();


        int confirmados = 0;
        int nconfirmados = 0;
        int nsabem = 0;
        int danoiva = 0;
        int donoivo = 0;

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


        int total = confirmados + nconfirmados + nsabem;


        ListView listView = (ListView) v.findViewById(R.id.listchart);

        List<Legendas> lista = new ArrayList<Legendas>();

        Legendas l1 = new Legendas();
        l1.Color = Color.BLUE;
        l1.Descricao =getResources().getString(R.string.title_confirmado) + "("+confirmados+")";
        l1.id = 0;

        Legendas l2 = new Legendas();
        l2.Color =Color.BLACK;
        l2.Descricao = getResources().getString(R.string.title_naoconfirmado) + "("+nconfirmados+")";
        l2.id = 2;

        Legendas l3 = new Legendas();
        l3.Color = Color.WHITE;
        l3.Descricao = getResources().getString(R.string.title_naosabe) + "("+nsabem+")";
        l3.id = 3;

        Legendas l4 = new Legendas();
        l4.Color = Color.WHITE;
        l4.Descricao =  getResources().getString(R.string.title_total)+ "("+total+")";
        l4.id = 4;

        lista.add(l1);
        lista.add(l2);
        lista.add(l3);
        lista.add(l4);
        listView.setAdapter(new LegendaConvidados(listView.getContext(), lista));


        return  v;
    }
}
