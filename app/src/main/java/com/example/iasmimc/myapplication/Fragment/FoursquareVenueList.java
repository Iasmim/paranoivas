package com.example.iasmimc.myapplication.Fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.iasmimc.myapplication.Adapters.LugarAdapter;
import com.example.iasmimc.myapplication.Class.Lugares;
import com.example.iasmimc.myapplication.R;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FoursquareVenueList extends Fragment {
    private ArrayAdapter<String> mFoursquareAdapter;
    private static final String ARG_PARAM1 = "param1";
    public static View _resultsView;
    public static  List<Lugares> listalugares;
    public FoursquareVenueList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public static FoursquareVenueList newInstance(int param1) {
        FoursquareVenueList fragment = new FoursquareVenueList();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Dummy data for the ListView. Here's the sample weekly forecast
        String[] data = {
                "Sample Foursquare Data",
        };

        List<String> foursquareList = new ArrayList<String>(Arrays.asList(data));

        View rootView = inflater.inflate(R.layout.activity_foursquare_venue_list, container, false);

        _resultsView = rootView;

        Button btnGetFoursquareData = (Button) rootView.findViewById(R.id.btnFoursquare);
        btnGetFoursquareData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchFoursquareDataTask fetch = new FetchFoursquareDataTask();
                fetch.execute("Floricultura");
            }
        });

        return rootView;
    }


    public class FetchFoursquareDataTask extends AsyncTask<String, Void, String[]> {
        private final String LOG_TAG = FetchFoursquareDataTask.class.getSimpleName();

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {

               if(listalugares != null) {

                   ListView listView = (ListView) _resultsView.findViewById(R.id.listviewfoursquare);


                   listView.setAdapter(new LugarAdapter(listView.getContext(), listalugares));
               }

            }

        }


        @Override
        protected String[] doInBackground(String... params) {

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
                // Build Foursquare URI with Parameters
                final String FOURSQUARE_BASE_URL =
                        "https://api.foursquare.com/v2/venues/search";
                final String client_id = "EJBO5TV44CVYLQPA3LXGVKAMYKEFPONIW130HUMXYGKLKB5I";
                final String client_secret = "PRLWIYDK3ARFB4XVSR3TBHNAFYL5ZLR0AOWDIS4VY2LJY41U";
                final String v = "20130815";
                final String near = "Manaus, Am";
                final String query = "Floricultura";
                final String limit = "12";

                Uri builtUri = Uri.parse(FOURSQUARE_BASE_URL).buildUpon()
                        .appendQueryParameter("client_id", client_id)
                        .appendQueryParameter("client_secret", client_secret)
                        .appendQueryParameter("v", v)
                        .appendQueryParameter("near", near)
                        .appendQueryParameter("query", query)
                        .appendQueryParameter("limit", limit)
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
                // If the code didn't successfully get the fpursquare data, there's no point in attempting
                // to parse it.
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