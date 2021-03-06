package com.example.iasmimc.myapplication.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iasmimc.myapplication.Class.Config;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;
import com.example.iasmimc.myapplication.RepositorioScript;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

public  class TimeRegreFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    View v;
    AdView adView;
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
        v =  inflater.inflate(R.layout.fragment_time, container, false);

        adView = new AdView(getActivity());
        adView.setAdUnitId("ca-app-pub-2299446572371245/3514097012");
        adView.setAdSize(AdSize.BANNER);
        adView.setBottom(0);
        LinearLayout layout = (LinearLayout)v.findViewById(R.id.timerhome);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();

       /* AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("ca-app-pub-2299446572371245/3514097012")
                .build();*/
        adView.loadAd(adRequest);

        return GetConfig();
    }

    @Override
    public void onStart() {
        GetConfig();
        super.onStart();
    }

    private View GetConfig() {

        repositorio = new RepositorioScript(getActivity());

        Config cv = new Config();

        cv = repositorio.getConf();

       // TextView noiva = (TextView)v.findViewById(R.id.nomeNoivahome);
       // TextView noivo = (TextView)v.findViewById(R.id.nomeNoivohome);

        //noiva.setText(cv.getNoiva());
       // noivo.setText(cv.getNoivo());

        int dia = cv.getDia();
        int mes = cv.getMes();
        int ano = cv.getAno();

        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH,dia);
        thatDay.set(Calendar.MONTH,mes); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, ano);
        Calendar today = Calendar.getInstance();

        if(dia == 0 && mes == 0 && ano ==0)
            thatDay = today;


        long diff =thatDay.getTimeInMillis() - today.getTimeInMillis(); //result in millis

        long days = diff / (24 * 60 * 60 * 1000);


        TextView dias = (TextView)v.findViewById(R.id.diasFaltam);
        dias.setText(""+days+"");
        return  v;
    }

}
