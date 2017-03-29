package com.yanisouakrim.newvision.Model.ArrayAdaptor;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yanisouakrim.newvision.Model.Film.Film;
import com.yanisouakrim.newvision.Model.Film.Genre;
import com.yanisouakrim.newvision.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by willi on 22/03/2017.
 */

public class FilmArrayAdapter extends ArrayAdapter
{

    LayoutInflater mInflater;
    List<Film> objets;
    Context mContext;

    public FilmArrayAdapter(Activity context, ArrayList<Film> items)
    {

        super(context, R.layout.list_of_movies, items);
        this.objets=items;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.list_of_movies, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        //(2) : Récupération des TextView de notre layout
        TextView titre = (TextView)layoutItem.findViewById(R.id.titre);
        TextView date = (TextView) layoutItem.findViewById(R.id.date);
        ImageView img = (ImageView) layoutItem.findViewById(R.id.poster);



        String lang= Locale.getDefault().toLanguageTag();


        //(3) : Renseignement des valeurs
        titre.setText(this.objets.get(position).getTitle());
        date.setText(this.objets.get(position).getRelease_date());
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w300"+this.objets.get(position).getPosterPath()).into(img);

        //(4) Changement de la couleur du fond de notre item
        /*if (mListP.get(position).genre == Personne.MASCULIN) {
            layoutItem.setBackgroundColor(Color.BLUE);
        } else {
            layoutItem.setBackgroundColor(Color.MAGENTA);
        }*/

        //On retourne l'item créé.
        return layoutItem;

    }

}
