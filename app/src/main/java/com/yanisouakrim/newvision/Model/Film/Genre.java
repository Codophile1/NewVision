package com.yanisouakrim.newvision.Model.Film;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yanisouakrim.newvision.NewVisionMain;
import com.yanisouakrim.newvision.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by willi on 22/03/2017.
 */

public class Genre implements Parcelable
{
    private int id;
    private String name;
    public static String genreCurrent;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Genre(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString()
    {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);
    }

    public static String getGenre( final int id, Context context, String lang)
    {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.GET,
                        "https://api.themoviedb.org/3/genre/movie/list?api_key=d091f93064f3b8e11ff8fde6b021699f&language="+lang,
                        new Response.Listener<String>() {

                            public void onResponse(String response) {

                                try {
                                    JSONArray ar = ((JSONObject) new JSONTokener(response).nextValue()).getJSONArray("genres");
                                    ArrayList<Genre> liste = new ArrayList<Genre>();
                                    Log.v("RECHERCHE : ",id+"");
                                    for (int i=0;i<ar.length();i++)
                                    {
                                        JSONObject genre= (JSONObject)ar.get(i);
                                        if (genre.getInt("id") == id)
                                        {
                                            Genre.genreCurrent=genre.getString("name");
                                            Log.v("TROUVE : ",Genre.genreCurrent+"");

                                            break;
                                        }

                                    }

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
        Log.v("Return : ",Genre.genreCurrent+"");

        return Genre.genreCurrent;
    }


}
