package com.example.iasmimc.myapplication;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

public  class TimeRegreFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

Repositorio repositorio;
    public  static TimeRegreFragment newInstance(int sectionNumber ) {
        TimeRegreFragment fragment = new TimeRegreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);


        return fragment;
    }

    public TimeRegreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        repositorio = new RepositorioScript(getActivity());

        Config cv = new Config();

      //  cv = repositorio.getConf();
        View v =  inflater.inflate(R.layout.fragment_time, container, false);
        TextView noiva = (TextView)v.findViewById(R.id.nomeNoivahome);
        TextView noivo = (TextView)v.findViewById(R.id.nomeNoivohome);

        noiva.setText(cv.getNoiva());
        noivo.setText(cv.getNoivo());

        int dia = cv.getDia();
        int mes = cv.getMes();
        int ano = cv.getAno();

        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH,dia);
        thatDay.set(Calendar.MONTH,mes); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, ano);

        Calendar today = Calendar.getInstance();

        long diff =thatDay.getTimeInMillis() - today.getTimeInMillis(); //result in millis

        long days = diff / (24 * 60 * 60 * 1000);



        TextView dias = (TextView)v.findViewById(R.id.diasFaltam);
        dias.setText(""+days+"");
        return  v;
    }
    
}
