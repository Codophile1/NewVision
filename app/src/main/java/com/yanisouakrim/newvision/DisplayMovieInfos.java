package com.yanisouakrim.newvision;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.yanisouakrim.newvision.Model.ArrayAdaptor.FilmArrayAdapter;
import com.yanisouakrim.newvision.Model.Film.CollectionFilm;
import com.yanisouakrim.newvision.Model.Film.Film;
import com.yanisouakrim.newvision.Model.Film.Genre;
import com.yanisouakrim.newvision.Model.Film.ProductionCompany;
import com.yanisouakrim.newvision.Model.Film.ProductionCountry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static android.net.Uri.parse;

public class DisplayMovieInfos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie_infos);

        // On récupère l'id du film
        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            return;
        }
        final String movieId = extras.getString("movieId");

        Log.v("film", movieId);
        final String lang= Locale.getDefault().toLanguageTag();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.GET,"https://api.themoviedb.org/3/movie/"+movieId+"?api_key=d091f93064f3b8e11ff8fde6b021699f&language="+lang,
                        new Response.Listener<String>() {

                            public void onResponse(String response)
                            {

                                try {
                                    JSONObject film = ((JSONObject) new JSONTokener(response).nextValue());
                                    CollectionFilm coll = null;
                                    if (!film.getString("belongs_to_collection").equals("null")){
                                        coll = new CollectionFilm(film.getJSONObject("belongs_to_collection").getInt("id"), film.getJSONObject("belongs_to_collection").getString("name"), film.getJSONObject("belongs_to_collection").getString("poster_path"));
                                    }
                                    ArrayList<Genre> genres = new ArrayList<Genre>();
                                    for(int i=0;i<film.getJSONArray("genres").length();i++)
                                    {
                                        genres.add(new Genre((((JSONObject)film.getJSONArray("genres").get(i)).getInt("id")), (((JSONObject)film.getJSONArray("genres").get(i)).getString("name"))));
                                        Log.v("ICI","genre");
                                    }
                                    ArrayList<ProductionCompany> prodComp = new ArrayList<ProductionCompany>();
                                    for(int i=0;i<film.getJSONArray("production_companies").length();i++){
                                        prodComp.add(new ProductionCompany((((JSONObject)film.getJSONArray("production_companies").get(i)).getInt("id")), (((JSONObject)film.getJSONArray("production_companies").get(i)).getString("name"))));
                                        Log.v("ICI","production_companies");

                                    }
                                    ArrayList<ProductionCountry> prodCount = new ArrayList<ProductionCountry>();
                                    for(int i=0;i<film.getJSONArray("production_countries").length();i++){
                                        prodCount.add(new ProductionCountry((((JSONObject)film.getJSONArray("production_countries").get(i)).getString("iso_3166_1")), (((JSONObject)film.getJSONArray("production_countries").get(i)).getString("name"))));
                                        Log.v("ICI","ProductionCountry");

                                    }

                                    final Film objFilm = new Film(film.getString("id"),film.getString("title"),film.getBoolean("adult"), coll, genres, film.getString("homepage"), film.getString("original_language"), film.getString("original_title"), film.getString("overview"), film.getString("popularity"), film.getString("poster_path"), prodComp, prodCount, film.getString("release_date"), film.getString("status"), film.getString("tagline"), film.getString("vote_average"), film.getInt("vote_count"));
                                    ImageView img = (ImageView)findViewById(R.id.poster_path);

                                    Picasso.with(DisplayMovieInfos.this).load("https://image.tmdb.org/t/p/w300"+objFilm.getPosterPath()).into(img);
                                    ((TextView)findViewById(R.id.titleFilm)).setText(objFilm.getTitle());
                                    ((TextView)findViewById(R.id.original_title)).setText(objFilm.getOriginalTitle());
                                    ((TextView)findViewById(R.id.original_language)).setText(objFilm.getOriginalLanguage());
                                    ((TextView)findViewById(R.id.release_date)).setText(objFilm.getRelease_date());
                                    ((TextView)findViewById(R.id.overview)).setText(objFilm.getOverview());
                                    ((TextView)findViewById(R.id.vote_average)).setText(objFilm.getVoteAverage());
                                    ((TextView)findViewById(R.id.popularity)).setText(objFilm.getPopularity());

                                    img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Uri page= parse(objFilm.getHomePage());
                                            Intent in = new Intent(Intent.ACTION_VIEW,page);
                                            if (in.resolveActivity(getPackageManager()) != null) {
                                                startActivity(in);
                                            }
                                        }
                                    });


                                    ArrayAdapter<ProductionCompany> aaProdComp=new ArrayAdapter<ProductionCompany>(DisplayMovieInfos.this, android.R.layout.simple_list_item_1,prodComp);
                                    ListView pdCony=(ListView)findViewById(R.id.prodCompanies);
                                    (pdCony).setAdapter(aaProdComp);


                                    ArrayAdapter<ProductionCountry> aaProdCoun=new ArrayAdapter<ProductionCountry>(DisplayMovieInfos.this, android.R.layout.simple_list_item_1,prodCount);
                                    ((ListView)findViewById(R.id.prodCountries)).setAdapter(aaProdCoun);

                                    //Log.v("film", objFilm.toString());

                                    /*Deuxieme requete pour récuperer des films similaires*/

                                    final int nbFilm = 4;
                                    Random r=new Random();
                                    Genre genre = genres.get(r.nextInt(genres.size()));


                                            RequestQueue queue2 = Volley.newRequestQueue(DisplayMovieInfos.this);
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
                                                                        Log.v("FILMs",liste.toString());
                                                                        FilmArrayAdapter aa=new FilmArrayAdapter(DisplayMovieInfos.this,liste);
                                                                        ListView visu = (ListView) findViewById(R.id.listeFilmSuggestion);
                                                                        visu.setAdapter(aa);

                                                                        // On ajoute un listener à la listView;

                                                                        visu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                            @Override
                                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                                Intent in = new Intent(DisplayMovieInfos.this,DisplayMovieInfos.class);
                                                                                String idMovie= ((Film)(((ListView) findViewById(R.id.listeFilmSuggestion)).getItemAtPosition(position))).getId();
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
                                            queue2.add(stringRequest);






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
