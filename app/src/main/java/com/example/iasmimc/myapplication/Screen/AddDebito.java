package com.example.iasmimc.myapplication.Screen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iasmimc.myapplication.Class.Convidados;
import com.example.iasmimc.myapplication.Class.Debito;
import com.example.iasmimc.myapplication.FloatingActionButton;
import com.example.iasmimc.myapplication.FloatingActionsMenu;
import com.example.iasmimc.myapplication.Fragment.FinanceiroFragment;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;
import com.example.iasmimc.myapplication.RepositorioScript;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.ads.*;

public class AddDebito extends ActionBarActivity {

    List<String> valores = null;
    Repositorio repositorio;
    private AdView adView;
   // private final String ANÚNCIO_ID = "ca-app-pub-2299446572371245/3514097012";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        valores = new ArrayList<>();
        setContentView(R.layout.activity_add_debito);



        Intent it = getIntent();
        TextView t = (TextView) findViewById(R.id.nomeparcela);
        TextView t1 = (TextView) findViewById(R.id.valorparcela);
        TextView t2 = (TextView) findViewById(R.id.vezes);
        TextView t3 = (TextView) findViewById(R.id.pagas);
        TextView t4 = (TextView) findViewById(R.id.id_parcela);
        Bundle bl = it.getExtras();

        if (bl != null) {
            t.setText(it.getStringExtra("nome"));
            t1.setText(it.getStringExtra("valor"));
            t2.setText(it.getStringExtra("vezes"));
            t3.setText(it.getStringExtra("pagas"));
            t4.setText(it.getStringExtra("id_parcela"));
        }

        // Criando o AdView.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-2299446572371245/3514097012");
        adView.setAdSize(AdSize.BANNER);


        // Recuperando o layout onde o anúncio vai ser exibido
        LinearLayout layout = (LinearLayout)findViewById(R.id.layoutdebito);

        // Adicionando o AdView no layout.
        layout.addView(adView,0);

        // Fazendo uma requisição para recuperar o anúncio.
          AdRequest adRequest = new AdRequest.Builder().build();

       /* AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("ca-app-pub-2299446572371245/3514097012")
                .build();*/

        // Adicionando a requisição no AdView.
        adView.loadAd(adRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_debito, menu);

        MenuItem save = menu.findItem(R.id.action_settings);
        save.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                repositorio = new RepositorioScript(getBaseContext());
                Debito c = new Debito();

                EditText txt = (EditText) findViewById(R.id.nomeparcela);
                EditText valor = (EditText) findViewById(R.id.valorparcela);
                EditText numvezes = (EditText) findViewById(R.id.vezes);
                EditText pagas = (EditText) findViewById(R.id.pagas);
                TextView idp = (TextView) findViewById(R.id.id_parcela);


                c.nome = (txt.getText().toString());
                c.parcelas = Integer.parseInt(numvezes.getText().toString());
                c.valor = Double.parseDouble(valor.getText().toString());
                c.pagas = Integer.parseInt(pagas.getText().toString());

                if(!idp.getText().toString().isEmpty())
                    c.id = Integer.parseInt(idp.getText().toString());

                if(c.pagas > c.parcelas) {
                    Toast.makeText(getApplicationContext(), R.string.numparcelasinvalidas, Toast.LENGTH_SHORT).show();
                    return false;
                }
                else {
                    repositorio.inserirDebito(c);
                    Toast.makeText(getApplicationContext(), R.string.txtDividacriada, Toast.LENGTH_SHORT).show();
                    setResult(0);
                    finish();
                    return true;
                }
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
}
