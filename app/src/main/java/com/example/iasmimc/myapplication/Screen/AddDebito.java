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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iasmimc.myapplication.Class.Convidados;
import com.example.iasmimc.myapplication.Class.Debito;
import com.example.iasmimc.myapplication.FloatingActionButton;
import com.example.iasmimc.myapplication.FloatingActionsMenu;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;
import com.example.iasmimc.myapplication.RepositorioScript;

import java.util.ArrayList;
import java.util.List;


public class AddDebito extends ActionBarActivity {

    List<String> valores = null;
    Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        valores = new ArrayList<>();
        setContentView(R.layout.activity_add_debito);



        Intent it = getIntent();
        TextView t = (TextView) findViewById(R.id.nomeparcela);

        Bundle bl = it.getExtras();

        if (bl != null)
            t.setText(it.getStringExtra("nome"));


        FloatingActionButton f = (FloatingActionButton) findViewById(R.id.pink_icon);

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });

    }


    public void onButtonClick() {
        // Creates the dialog if necessary, then shows it.
        // Will show the same dialog if called multiple times.
        showDialog(0);
    }

    /**
     * Called to create a dialog to be shown.
     */
    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case 0:
                return createExampleDialog();
            default:
                return null;
        }
    }

    /**
     * Create and return an example alert dialog with an edit text box.
     */
    private Dialog createExampleDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.addparcela);
        builder.setMessage(R.string.valorparcela);

        // Use an EditText view to get user input.
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                valores.add(value);
                ListView viewList = (ListView) findViewById(R.id.listadividas);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_list_item_1, valores);
                viewList.setAdapter(adapter);

                return;
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_debito, menu);
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


            repositorio = new RepositorioScript(getBaseContext());
            Debito c = new Debito();

            EditText txt = (EditText) findViewById(R.id.nomeparcela);

            c.nome = (txt.getText().toString());
            List<Debito> lista = new ArrayList<>();
            ListView viewList = (ListView) findViewById(R.id.listadividas);


            int count = viewList.getCount();
            Double valores[] = new Double[count];
            Double valor = 0.0;
            for (int i = 0; i < count; i++) {
                TextView row = (TextView) viewList.getChildAt(i);
                valores[i] = Double.parseDouble(row.getText().toString());
                valor += Double.parseDouble(row.getText().toString());
            }

            c.parcelas = count;
            c.valor = valor;
            repositorio.inserirDebito(c);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
