package com.example.iasmimc.myapplication.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iasmimc.myapplication.Adapters.ConvidadosAdapter;
import com.example.iasmimc.myapplication.Adapters.FinanceiroAdapter;
import com.example.iasmimc.myapplication.Class.Convidados;
import com.example.iasmimc.myapplication.Class.Debito;
import com.example.iasmimc.myapplication.FloatingActionButton;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;
import com.example.iasmimc.myapplication.RepositorioScript;
import com.example.iasmimc.myapplication.Screen.AddDebito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class FinanceiroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    Menu _menu;
    // TODO: Rename and change types of parameters
    private String mParam1;
    Repositorio repositorio;
    static View rootView;
    List<Debito> currentDebit;
    ListView listView;
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
        currentDebit = new ArrayList<>();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.main, menu);
        _menu = menu;
        MenuItem trash = menu.findItem(R.id.action_excluir);
        trash.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                // Setting Dialog Title
                alertDialog.setTitle(R.string.title_alert);
                // Setting Dialog Message
                alertDialog.setMessage(R.string.mss_alert);
                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.ic_action_cancel);
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        TextView id = (TextView) rootView.findViewById(R.id.id_convidado);

                        for (Debito debito : currentDebit) {
                            repositorio.deletarDebito(debito.id);
                        }


                        ListView listView = (ListView) rootView.findViewById(R.id.listviewfinanceira);
                        repositorio = new RepositorioScript(getActivity());
                        List<Debito> lista = new ArrayList<>();
                        lista = repositorio.ListarDebito();
                        listView.setAdapter(new FinanceiroAdapter(listView.getContext(), lista));
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton(R.string.alert_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
                MenuItem trash = _menu.findItem(R.id.action_excluir);
                trash.setVisible(false);
                return true;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_excluir:
                Toast.makeText(getActivity(), "Testando 1 2 3...", Toast.LENGTH_LONG);
                break;
        }
        return true;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_financeiro, container, false);
        listView = (ListView) rootView.findViewById(R.id.listviewfinanceira);

        FloatingActionButton action_debito = (FloatingActionButton) rootView.findViewById(R.id.icon_mais_fin);


        final Intent deb = new Intent(rootView.getContext(), AddDebito.class);
        action_debito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(deb, 0);
            }
        });

        repositorio = new RepositorioScript(rootView.getContext());

        List<Debito> lista = new ArrayList<>();

        lista = repositorio.ListarDebito();

        listView.setAdapter(new FinanceiroAdapter(listView.getContext(), lista));


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(getResources().getColor(R.color.random15));

                MenuItem ex = _menu.findItem(R.id.action_excluir);
                currentDebit.add((Debito) parent.getItemAtPosition(position));
                ex.setVisible(true);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MenuItem ex = _menu.findItem(R.id.action_excluir);


                if (!ex.isVisible()) {
                    final Intent deb = new Intent(adapterView.getContext(), AddDebito.class);

                    Bundle params = new Bundle();

                    TextView name = (TextView) view.findViewById(R.id.nomeDebito);
                    TextView qtde = (TextView) view.findViewById(R.id.qtde);
                    TextView valor = (TextView) view.findViewById(R.id.valor);
                    TextView totalpgas = (TextView) view.findViewById(R.id.pagas);
                    TextView id = (TextView) view.findViewById(R.id.id_parcela);

                    params.putString("nome", name.getText().toString());
                    params.putString("valor", valor.getText().toString());
                    params.putString("vezes", qtde.getText().toString());
                    params.putString("pagas", totalpgas.getText().toString());
                    params.putString("id_parcela", id.getText().toString());
                    deb.putExtras(params);

                    startActivityForResult(deb, 0);

                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0)
        {
            List<Debito> lista = new ArrayList<>();

            lista = repositorio.ListarDebito();

            listView.setAdapter(new FinanceiroAdapter(listView.getContext(), lista));

        }
    }
}


