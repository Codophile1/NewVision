package com.yanisouakrim.newvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class ListingMovies extends AppCompatActivity {
    //li
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
        final Integer nbFilm = extras.getInt("nbFilms");
        Genre genre = (Genre)extras.get("genre");

        if (nbFilm != 0 && genre!=null)
        {
            final String lang= Locale.getDefault().toLanguageTag();

            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest =
                    new StringRequest(
                            Request.Method.GET,
                            "https://api.themoviedb.org/3/genre/"+genre.getId()+"/movies?api_key=d091f93064f3b8e11ff8fde6b021699f&language="+lang+"&include_adult=false&sort_by=created_at.asc",
                            new Response.Listener<String>() {

                                public void onResponse(String response) {

                                    try {
                                        JSONArray ar = ((JSONObject) new JSONTokener(response).nextValue()).getJSONArray("results");
                                        ArrayList<Film> liste=new ArrayList<Film>();

                                        JSONObject film;
                                        for (int i = 0;i<ar.length() && i<nbFilm;i++)
                                        {
                                            film=ar.getJSONObject(i);
                                            liste.add(new Film(
                                                    film.getString("id"),
                                                    film.getString("title"),
                                                    film.getString("release_date"),
                                                    film.getString("poster_path")
                                            ));

                                        }
                                        FilmArrayAdapter aa=new FilmArrayAdapter(ListingMovies.this,liste);
                                        ListView visu = (ListView) findViewById(R.id.listeFilm);
                                        visu.setAdapter(aa);

                                        // On ajoute un listener à la listView;

                                        visu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent in = new Intent(ListingMovies.this,DisplayMovieInfos.class);
                                                String idMovie= ((Film)(((ListView) findViewById(R.id.listeFilm)).getItemAtPosition(position))).getId();
                                                in.putExtra("movieId",idMovie);
                                                startActivity(in);
                                            }
                                        });

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
