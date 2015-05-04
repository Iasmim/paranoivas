package com.example.iasmimc.myapplication.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.iasmimc.myapplication.Adapters.FinanceiroAdapter;
import com.example.iasmimc.myapplication.Class.Debito;
import com.example.iasmimc.myapplication.FloatingActionButton;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;
import com.example.iasmimc.myapplication.RepositorioScript;
import com.example.iasmimc.myapplication.Screen.AddDebito;

import java.util.ArrayList;
import java.util.List;



public class FinanceiroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    Repositorio repositorio;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FinanceiroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinanceiroFragment newInstance(int param1) {
        FinanceiroFragment fragment = new FinanceiroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public FinanceiroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_financeiro, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listviewfinanceira);

        FloatingActionButton action_debito = (FloatingActionButton) rootView.findViewById(R.id.icon_mais_fin);


        final Intent deb = new Intent(rootView.getContext(), AddDebito.class);
        action_debito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(deb);
            }
        } );

        repositorio = new RepositorioScript(rootView.getContext());

        List<Debito> lista = new ArrayList<>();

        lista = repositorio.ListarDebito();

        listView.setAdapter(new FinanceiroAdapter(listView.getContext(), lista));
        return  rootView;
    }


}


