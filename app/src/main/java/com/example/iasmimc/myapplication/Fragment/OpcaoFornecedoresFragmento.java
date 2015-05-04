package com.example.iasmimc.myapplication.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import com.example.iasmimc.myapplication.Adapters.OpcaoFornecedoresAdapter;
import com.example.iasmimc.myapplication.Class.Lugares;
import com.example.iasmimc.myapplication.Class.SelecionaFornecedor;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;

public class OpcaoFornecedoresFragmento extends Fragment {
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
    public static OpcaoFornecedoresFragmento newInstance(int param1) {
        OpcaoFornecedoresFragmento fragment = new OpcaoFornecedoresFragmento();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public OpcaoFornecedoresFragmento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_op_forne, container, false);
        final ListView listView = (ListView) rootView.findViewById(R.id.listviewopforne);


        final SearchView search = (SearchView) rootView.findViewById(R.id.searchView);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent it = new Intent(getActivity(), SelecionaFornecedor.class);
                Bundle params = new Bundle();
                String selectedFromList = query;
                params.putString("nome",selectedFromList);
                it.putExtras(params);
                startActivity(it);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        List<Lugares> lista = new ArrayList<>();

        lista = new ArrayList<>();

       String[] opcao = new String[]{"Alianças",
               "Aluguel de carro"
               ,"Animação de festa"
               ,"Aula de dança"
               ,"Banda"
               ,"Bar e barman"
               ,"Bebidas"
               ,"Bem casados"
               ,"Bolo"
               ,"Buffet"
               ,"Cerimonialista"
               ,"Cabine fotografica"
               ,"Caligrafos"
               ,"Celebrante"
               ,"Mestre de cerimônia"
               ,"Convites"
               ,"Decoração"
               ,"Dia da noiva"
               ,"DJ"
               ,"Doces"
               ,"Equipamentos para festa"
               ,"Espaço para casamentos"
               ,"Filmagem"
               ,"Floricultura"
               ,"Fotografia"
               ,"Lembrancinhas"
               ,"Locação de moveis"
               ,"Lua de mel"
               ,"Musicos"
               ,"Outros serviços"
               ,"Papelaria"
               ,"Roupa do noivo"
               ,"Sapato e acessorios"
               ,"Topo de bolo "
               ,"Vestido da noiva"
               ,"Floricultura"};
        for (int i = 0; i < opcao.length; i++) {
                Lugares n = new Lugares();
                n.name = opcao[i];
                lista.add(n);

        }

        listView.setAdapter(new OpcaoFornecedoresAdapter(listView.getContext(), lista));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), SelecionaFornecedor.class);
                Bundle params = new Bundle();
                Lugares selectedFromList =(Lugares)listView.getItemAtPosition(position);
                params.putString("nome",selectedFromList.name);
                it.putExtras(params);
                startActivity(it);
            }
        });
        return  rootView;
    }

}


