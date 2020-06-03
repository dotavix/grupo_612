package com.example.becareful;

import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class InfoActivity extends AppCompatActivity {

    TextView mEditText;
    Button mButton;

    private static final String TAG = "searchApp";
    static String result = "";
    Integer responseCode = null;
    String responseMessage = "";
    RatingBar ratingbar;
    Button buttonFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Log.d(TAG, "**** APP START ****");

        mEditText = findViewById(R.id.editInfo);
        mButton = findViewById(R.id.botonInfo);
        ratingbar = findViewById(R.id.ratingBar);
        buttonFinal = findViewById(R.id.buttonFin);

        mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                final String searchString = "cuidados a mi celular";

                String searchStringNoSpaces = searchString.replace(" ", "+");

                // Your Google API key
                String key= "AIzaSyC4S7x5LBNelDdyG8ZY-MKS0O-y9q4RSKw";

                // Your Google Search Engine ID
                String cx = "012987211185105791696:kk3yfkffllx";

                String urlString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";
                URL url = null;
                try {

                    url = new URL(urlString);

                } catch (MalformedURLException e) {
                    Log.e(TAG, "ERROR converting String to URL " + e.toString());
                }
                Log.d(TAG, "Url = "+  urlString);


                // start AsyncTask
                GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
                searchTask.execute(url);

            }
        });

        buttonFinal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String rating=String.valueOf(ratingbar.getRating());
                Toast.makeText(getApplicationContext(), "Gracias por tu puntación de " + rating, Toast.LENGTH_LONG).show();

            }
        });

    }

    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        protected void onPreExecute() {

            Log.d(TAG, "AsyncTask - onPreExecute");

        }


        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];
            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }


            try {
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());

            }

            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);

            try {

                if (responseCode != null && responseCode == 200) {


                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;

                    while ((line = rd.readLine()) != null) {

                              result+=line;

                    }
                    rd.close();

                    conn.disconnect();

                    return result;

                } else {

                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Are you online ? " + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return result;

                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());

            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);

        }

        protected void onPostExecute(String result) {

            Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);

            JSONObject jObj = null;
            try {
                jObj = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String dato = "";

            if (jObj.has("items")) {

                JSONArray nuevo = jObj.optJSONArray("items");
                try {

                    JSONObject info;

                    for (int i= 0 ; i< nuevo.length() ;i ++) {

                        info = nuevo.getJSONObject(i);

                        dato = dato + "Recomendado: " + info.optString("link") + "\n" +"\n" ;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            mEditText.setText(dato);
            mEditText.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}

