package com.example.iasmimc.myapplication.Screen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iasmimc.myapplication.Class.Convidados;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;
import com.example.iasmimc.myapplication.RepositorioScript;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class ConvidadosAdd extends ActionBarActivity
{
    private String  array_spinner[];
    private String  array_spinner1[];
    public static Repositorio repositorio;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convidados_add);
        ImageButton ibt = (ImageButton)findViewById(R.id.plus);
        ibt.setImageResource(R.drawable.ic_action_add);





        ibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText qtde = (EditText) findViewById(R.id.qtdeConv);
                int count =   Integer.parseInt(qtde.getText().toString());
                count++;
                qtde.setText(String.valueOf(count));
            }
        });



       Intent it = getIntent();

       Bundle bl = it.getExtras();

        if(bl != null)
        {
            EditText txt = (EditText) findViewById(R.id.nomeConv);
            txt.setText(it.getStringExtra("nome"));

            EditText qtde = (EditText) findViewById(R.id.qtdeConv);
            qtde.setText(it.getStringExtra("qtde"));

            RadioButton sp1 = (RadioButton) findViewById(R.id.radio_noiva);
            RadioButton sp = (RadioButton) findViewById(R.id.radio_noivo);


            RadioButton sim = (RadioButton) findViewById(R.id.convidadosim);
            RadioButton no = (RadioButton) findViewById(R.id.convidadono);
            RadioButton talvez = (RadioButton) findViewById(R.id.convidadotalvez);


            if(it.getStringExtra("tipo").equals("0"))
              sp1.setChecked(true);
            else
                sp.setChecked(true);


            if(it.getStringExtra("confirma").equals("0"))
                sim.setChecked(true);
            else  if(it.getStringExtra("confirma").equals("1"))
               no.setChecked(true);
            else
                talvez.setChecked(true);

            TextView id = (TextView) findViewById(R.id.id_convidado);
            id.setText(it.getStringExtra("id"));
        }


        // Criando o AdView.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-2299446572371245/4046990617");
        adView.setAdSize(AdSize.BANNER);



        // Recuperando o layout onde o anúncio vai ser exibido
        LinearLayout layout = (LinearLayout)findViewById(R.id.layoutaddconvidado);

        // Adicionando o AdView no layout.
        layout.addView(adView,8);

        // Fazendo uma requisição para recuperar o anúncio.
         AdRequest adRequest = new AdRequest.Builder().build();
/*
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("ca-app-pub-2299446572371245/4046990617")
                .build();*/

        // Adicionando a requisição no AdView.
        adView.loadAd(adRequest);
    }
    @Override
    protected void onPause() {
        //Pausando o AdView ao pausar a activity
        adView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Resumindo o AdView ao resumir a activity
        adView.resume();
    }

    @Override
    protected void onDestroy() {
        //Destruindo o AdView ao destruir a activity
        adView.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_convidados_add, menu);

        MenuItem save = menu.findItem(R.id.action_save);
        save.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                repositorio = new RepositorioScript(getBaseContext());
                Convidados c = new Convidados();

                EditText txt = (EditText) findViewById(R.id.nomeConv);
                EditText qtde = (EditText) findViewById(R.id.qtdeConv);

                RadioButton sp1 = (RadioButton) findViewById(R.id.radio_noiva);
                RadioButton sp = (RadioButton) findViewById(R.id.radio_noivo);


                RadioButton sim = (RadioButton) findViewById(R.id.convidadosim);
                RadioButton no = (RadioButton) findViewById(R.id.convidadono);
                RadioButton talvez = (RadioButton) findViewById(R.id.convidadotalvez);

                if (sp1.isChecked())
                    c.setTipo(0);
                else
                    c.setTipo(1);

                if (no.isChecked())
                    c.setConfirmado(1);
                else if (talvez.isChecked())
                    c.setConfirmado(2);
                else
                    c.setConfirmado(0);

                c.setNome(txt.getText().toString());
                c.setQtde(Integer.valueOf(qtde.getText().toString()));


                repositorio.inserirConvidado(c);
                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "Contato Criado.", Toast.LENGTH_SHORT).show();
                c.setNome("");
                c.setQtde(0);
                c.setTipo(0);
                c.setConfirmado(0);
                setResult(0);
                finish();
                return true;
            }
        });

        return true;
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
}
