package com.example.iasmimc.myapplication.Screen;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iasmimc.myapplication.Class.Config;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Repositorio;
import com.example.iasmimc.myapplication.RepositorioScript;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Configuracao extends ActionBarActivity {
    private Button botao;
    Repositorio repositorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        repositorio = new RepositorioScript(getBaseContext());

        Config cv = new Config();

        cv = repositorio.getConf();

        if(cv.getNoiva() != null)
        {

            EditText noiva = (EditText)findViewById(R.id.noivaNome);
            EditText noivo = (EditText)findViewById(R.id.noivoNome);
            DatePicker data = (DatePicker)findViewById(R.id.dialog_date_datePicker);

            noiva.setText(cv.getNoiva());
            noivo.setText(cv.getNoivo());

            int   day  = cv.getDia();
            int   month= cv.getMes();
            int   year = cv.getAno();

            data.init(year, month, day, null);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuracao, menu);

        MenuItem save = menu.findItem(R.id.conf_save);

        save.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                repositorio = new RepositorioScript(getBaseContext());

                EditText noiva = (EditText)findViewById(R.id.noivaNome);
                EditText noivo = (EditText)findViewById(R.id.noivoNome);
                DatePicker data = (DatePicker)findViewById(R.id.dialog_date_datePicker);
                Config c = new Config();
                c.setNoiva(noiva.getText().toString());
                c.setNoivo(noivo.getText().toString());
                int   day  = data.getDayOfMonth();
                int   month= data.getMonth();
                int   year = data.getYear();
                c.setDia(day);
                c.setMes(month);
                c.setAno(year);

                Calendar cal = Calendar.getInstance();

                Calendar calmarrie = Calendar.getInstance();
                calmarrie.set(Calendar.YEAR, year);
                calmarrie.set(Calendar.DAY_OF_MONTH, day);
                calmarrie.set(Calendar.MONTH, month);

                SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy");

                Date today = cal.getTime();
                Date marrie = calmarrie.getTime();
                int retorno = compareTwoDates(today, marrie);

                if(retorno == 1) {
                    Toast.makeText(getBaseContext(), R.string.datainvalida, Toast.LENGTH_SHORT).show();
                    return  false;
                }
                else {

                    repositorio.atulizaConfig(c);

                    Toast.makeText(getBaseContext(), "Dados salvos com sucesso.", Toast.LENGTH_SHORT).show();
                    setResult(1);
                    finish();
                    return  true;
                }

            }
        });

        return true;
    }
    public static int compareTwoDates(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            int retVal = date1.compareTo(date2);

            if (retVal > 0)
                return 1; // date1 is greatet than date2
            else if (retVal == 0) // both dates r equal
                return 0;

        }
        return -1; // date1 is less than date2
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
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        View v = super.onCreateView(parent, name, context, attrs);


        return v;


    }
}
