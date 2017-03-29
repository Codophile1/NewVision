package com.yanisouakrim.newvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yanisouakrim.newvision.Model.ArrayAdaptor.FilmArrayAdapter;
import com.yanisouakrim.newvision.Model.Film.Film;
import com.yanisouakrim.newvision.Model.Film.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Locale;

public class NewVisionMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vision_main);

        Spinner spinner = (Spinner) findViewById(R.id.nb_films_spinner);
        // On créer un ArrayAdapter pour le Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.nb_films , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Test
        spinner.setAdapter(adapter);
        String lang=Locale.getDefault().toLanguageTag();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.GET,
                        "https://api.themoviedb.org/3/genre/movie/list?api_key=d091f93064f3b8e11ff8fde6b021699f&language="+lang,
                        new Response.Listener<String>() {

                            public void onResponse(String response) {

                                try {
                                    JSONArray ar = ((JSONObject) new JSONTokener(response).nextValue()).getJSONArray("genres");
                                    ArrayList<Genre> liste = new ArrayList<Genre>();
                                    for (int i=0;i<ar.length();i++)
                                   {
                                        JSONObject genre= (JSONObject)ar.get(i);
                                        liste.add(new Genre(Integer.parseInt(genre.getString("id")),genre.getString("name")));
                                   }

                                    ArrayAdapter<Genre> aa= new ArrayAdapter<Genre>(NewVisionMain.this, android.R.layout.simple_list_item_1,liste);
                                    ((Spinner)findViewById(R.id.type_films)).setAdapter(aa);
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

        ((Button)findViewById(R.id.Search)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent in = new Intent(NewVisionMain.this,ListingMovies.class);
                        int nbFilm=(Integer.parseInt((((Spinner)findViewById(R.id.nb_films_spinner)).getSelectedItem())+""));
                        Log.v("nbFilm",((Spinner)findViewById(R.id.nb_films_spinner)).getSelectedItem()+"");

                        Genre genre=(Genre)(((Spinner)findViewById(R.id.type_films)).getSelectedItem());
                        in.putExtra("nbFilms",nbFilm);
                        in.putExtra("genre",genre);
                        startActivity(in);
                    }
        });
    }




}
