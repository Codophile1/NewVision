package com.yanisouakrim.newvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yanisouakrim.newvision.Model.ArrayAdaptor.FilmArrayAdapter;
import com.yanisouakrim.newvision.Model.Film.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class ListingMovies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_movies);
        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            return;
        }
        String commune = extras.getString(Intent.EXTRA_TEXT);
        if (commune != null)
        {

            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest =
                    new StringRequest(
                            Request.Method.GET,
                            "https://nosql-workshop.herokuapp.com/api/installations/search?query="+commune,
                            new Response.Listener<String>() {

                                public void onResponse(String response) {

                                    try {
                                        JSONArray ar = (JSONArray) new JSONTokener(response).nextValue();
                                        JSONObject obj;
                                        String activites="";
                                        ArrayList<Film> liste=new ArrayList<Film>();

                                        for (int i = 0;i<ar.length();i++)
                                        {
                                            obj=ar.getJSONObject(i);
                                            String nom = obj.getString("nom");
                                            JSONObject adresse = obj.getJSONObject("adresse");
                                            JSONArray equipements = obj.getJSONArray("equipements");
                                            JSONObject equipement=equipements.getJSONObject(0);
                                            try
                                            {
                                                JSONArray toutesActivites=equipement.getJSONArray("activites");
                                                activites="";
                                                for(int act=0;act<toutesActivites.length();act++)
                                                {
                                                    activites+=toutesActivites.get(act)+" ";
                                                }
                                            }catch (Exception e)
                                            {
                                                activites+="";
                                            }

                                            int numero=0;
                                            try
                                            {
                                                numero=Integer.parseInt(adresse.getString("numero"));
                                            }catch (Exception e)
                                            {
                                                numero=0;
                                            }
                                            liste.add(new Film(nom, numero, adresse.getString("voie"),adresse.getString("codePostal"),activites));


                                        }
                                        //   Log.v("JSONOBJET",ar.toString());
                                        FilmArrayAdapter aa=new FilmArrayAdapter(ListingMovies.this,liste);
                                        ListView visu = (ListView) findViewById(R.id.ListeLISTVIEW);
                                        visu.setAdapter(aa);

                                    } catch (JSONException je) {
                                        Log.e("Errrr", je.getMessage());
                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                    Log.v("VOLLEY", "err :  "+error.getMessage());
                                }
                            });
            queue.add(stringRequest);
        }
    }
}
