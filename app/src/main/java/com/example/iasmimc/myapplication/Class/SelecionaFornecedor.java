package com.example.iasmimc.myapplication.Class;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.iasmimc.myapplication.Adapters.LugarAdapter;
import com.example.iasmimc.myapplication.R;
import com.example.iasmimc.myapplication.Screen.AddDebito;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;


public class SelecionaFornecedor extends ActionBarActivity {
    public static List<Lugares> listalugares;
    ListView listView;
    String place;
    private static final int PROGRESS = 0x1;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    int pos;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleciona_fornecedor);
        listView = (ListView) this.findViewById(R.id.listviewselforne);
        mProgress = (ProgressBar) findViewById(R.id.progresso);
        mProgress.setVisibility(View.INVISIBLE);
        Intent it = getIntent();
        TextView t = (TextView) findViewById(R.id.textoerror);
        t.setText("");
        Bundle bl = it.getExtras();

        if (bl != null)
            place = it.getStringExtra("nome");


        FetchFoursquareDataTask fetch = new FetchFoursquareDataTask();
        fetch.execute(place);


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SelecionaFornecedor.this);
                    // Setting Dialog Title
                    alertDialog.setTitle(R.string.myforne);
                    // Setting Dialog Message
                    alertDialog.setMessage(R.string.textforn);
                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.ic_action_cancel);
                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent it = new Intent(getBaseContext(), AddDebito.class);
                            Bundle params = new Bundle();
                            Lugares selectedFromList =(Lugares)listView.getItemAtPosition(pos);
                            params.putString("nome",selectedFromList.name);
                            it.putExtras(params);
                            startActivity(it);

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

                    return true;
                }
            });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seleciona_fornecedor, menu);
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

    public class FetchFoursquareDataTask extends AsyncTask<String, Void, String[]> {
        private final String LOG_TAG = FetchFoursquareDataTask.class.getSimpleName();

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                if (listalugares != null) {
                    listView.setAdapter(new LugarAdapter(listView.getContext(), listalugares));
                    if(listalugares.size() == 0)
                    {
                        TextView t = (TextView) findViewById(R.id.textoerror);
                        t.setVisibility(View.VISIBLE);
                        t.setText(R.string.returnmsg);
                    }

                    listalugares = null;
                }
            }
        }


        @Override
        protected String[] doInBackground(String... params) {
            mProgress.setVisibility(View.VISIBLE);
            // If there's no venue category, theres nothing to look up. Verify the size of the params.
            if (params.length == 0) {
                return null;
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String foursquareJsonStr = null;

            try {
                mProgress.setVisibility(View.VISIBLE);
                // Build Foursquare URI with Parameters
                final String FOURSQUARE_BASE_URL =
                        "https://api.foursquare.com/v2/venues/search";
                final String client_id = "EJBO5TV44CVYLQPA3LXGVKAMYKEFPONIW130HUMXYGKLKB5I";
                final String client_secret = "PRLWIYDK3ARFB4XVSR3TBHNAFYL5ZLR0AOWDIS4VY2LJY41U";
                final String v = "20130815";
                final String near = "Manaus, Am";
                final String query = place;
                //final String limit = "12";

                Uri builtUri = Uri.parse(FOURSQUARE_BASE_URL).buildUpon()
                        .appendQueryParameter("client_id", client_id)
                        .appendQueryParameter("client_secret", client_secret)
                        .appendQueryParameter("v", v)
                        .appendQueryParameter("near", near)
                        .appendQueryParameter("query", query)
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to Foursquare, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    foursquareJsonStr = null;
                    return null;
                }
                foursquareJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Foursquare JSON String: " + foursquareJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            String[] list = new String[]{"", ""};
            try {

                JSONObject foursquareJson = new JSONObject(foursquareJsonStr);
                JSONObject responseObject = (JSONObject) foursquareJson.get("response");
                JSONArray foursquareArray = responseObject.getJSONArray("venues");
                list = new String[foursquareArray.length()];


                listalugares = new ArrayList<>();
                for(int i=0;i< foursquareArray.length();i++){
                    JSONObject lines = (JSONObject) new JSONTokener(foursquareArray.getString(i)).nextValue();

                    // JSONArray location = lines.getJSONArray("");

                    Lugares l = new Lugares();
                    l._id = lines.getString("id");
                    l.name = lines.getString("name");


                    l.formattedAddress = lines.getJSONObject("location").getString("formattedAddress");
                    l.formattedAddress = l.formattedAddress.replace("[","").replace("]","").replace("\"", " ");
                    JSONObject contact = lines.getJSONObject("contact");

                    JSONArray categories = lines.getJSONArray("categories");

                    for (int k = 0; k < categories.length() ; k++) {
                        JSONObject row = categories.getJSONObject(k);

                        JSONObject icon = row.getJSONObject("icon");
                        if(icon.length() > 0)
                            l.icon = icon.getString("prefix") +"bg_64"+ icon.getString("suffix");
                    }


                    if(contact.length() > 0)
                        l.contact = contact.getString("phone");


                    listalugares.add(l);

                }

                mProgress.setVisibility(View.INVISIBLE);




                return list;
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            } finally {
                Log.i(LOG_TAG, "ba");

                return list;
            }

        }

    }
}
