package com.example.iasmimc.myapplication.Screen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.iasmimc.myapplication.Adapters.ConvidadosAdapter;
import com.example.iasmimc.myapplication.Class.Convidados;
import com.example.iasmimc.myapplication.FloatingActionButton;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;
import com.example.iasmimc.myapplication.RepositorioScript;
import com.example.iasmimc.myapplication.TabHostFragment;

import java.util.ArrayList;
import java.util.List;


public class ListConvActivity extends ActionBarActivity {


public  static List<Convidados> checkList;
    public static Repositorio repositorio;
    public static Intent intentconvidadoCurrent;
    public static List<Convidados> currentConvidado;
    static ActionBar actionBar;
    static Menu _menu;
    static Intent it;
    static View rootView;
    static ListView listView;
    public ListConvActivity()
    {
        currentConvidado = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkList = new ArrayList<>();
        setContentView(R.layout.activity_principal);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TabHostFragment())
                    .commit();
        }

    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.main, menu);
      _menu = menu;
        actionButtonInvides(menu);
        return true;
    }


    public  void actionButtonInvides(Menu menu)
    {

        _menu = menu;

        MenuItem action_conf = menu.findItem(R.id.action_conf);
        final Intent conf = new Intent(this, Configuracao.class);
        action_conf.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                startActivityForResult(conf, 1);
                return  true;
            }
        });


        MenuItem trash = menu.findItem(R.id.action_excluir);
        trash.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListConvActivity.this);
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

                        for (Convidados convidados : currentConvidado) {
                            repositorio.deletarConvidados(convidados.getId());

                        }


                        ListView listView = (ListView) rootView.findViewById(R.id.listview);
                        repositorio = new RepositorioScript(getBaseContext());
                        List<Convidados> lista = new ArrayList<>();
                        lista = repositorio.ListarConvidados();
                        listView.setAdapter(new ConvidadosAdapter(listView.getContext(), lista));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public  static void  callListInvides(View view,AdapterView<?> parent,int position)
    {

        intentconvidadoCurrent = new Intent(view.getContext(), ConvidadosAdd.class);
        Bundle params = new Bundle();

        TextView name = (TextView) view.findViewById(R.id.convidado);
        TextView qtde = (TextView) view.findViewById(R.id.qtde);
        TextView id = (TextView) view.findViewById(R.id.id_convidado);

        params.putString("nome",name.getText().toString());
        params.putString("qtde",qtde.getText().toString());
        params.putString("id",id.getText().toString());

        Convidados c = (Convidados)parent.getItemAtPosition(position);
        currentConvidado.add(c);
        if(c.getTipo() == 0)
            params.putString("tipo","0");
        else
            params.putString("tipo","1");

        if(c.isConfirmado() == 0)
            params.putString("confirma","0");
        else if(c.isConfirmado() == 1)
            params.putString("confirma","1");
        else
            params.putString("confirma", "2");


        intentconvidadoCurrent.putExtras(params);

    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_principal, container, false);
            listView = (ListView) rootView.findViewById(R.id.listview);

            FloatingActionButton f = (FloatingActionButton) rootView.findViewById(R.id.pink_icon);
            it = new Intent(rootView.getContext(), ConvidadosAdd.class);
            f.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivityForResult(it, 0);

                }
            });


            repositorio = new  RepositorioScript(rootView.getContext());

            List<Convidados> lista = new ArrayList<>();

            lista = repositorio.ListarConvidados();

            listView.setAdapter(new ConvidadosAdapter(listView.getContext(), lista));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    MenuItem ex = _menu.findItem(R.id.action_excluir);

                    callListInvides(view, parent, position);
                    if(!ex.isVisible()) {
                        startActivityForResult(intentconvidadoCurrent, 0);
                    }
                }
            });


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setBackgroundColor(getResources().getColor(R.color.random15));

                    MenuItem ex = _menu.findItem(R.id.action_excluir);
                    ex.setVisible(true);
                    callListInvidesDelete(view, parent, position);
                    return false;
                }
            });
            return rootView;
        }


        public  static void  callListInvidesDelete(View view,AdapterView<?> parent,int position)
        {

            Convidados c = (Convidados)parent.getItemAtPosition(position);
            if(checkList.contains(c))
                checkList.remove(c);
            else
                checkList.add(c);

        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode == 0)
            {
                List<Convidados> lista = new ArrayList<>();

                lista = repositorio.ListarConvidados();

                listView.setAdapter(new ConvidadosAdapter(listView.getContext(), lista));
            }
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

    }


}
