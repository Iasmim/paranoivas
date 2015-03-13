package com.example.iasmimc.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class AddDebito extends ActionBarActivity {

    List<String> valores = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        valores = new ArrayList<>();
        setContentView(R.layout.activity_add_debito);
        ImageView bt = (ImageView) this.findViewById(R.id.addmais);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });
    }



    public void onButtonClick(View button) {
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
       // input.setId(TEXT_ID);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
