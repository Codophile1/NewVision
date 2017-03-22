package com.yanisouakrim.newvision.Model.ArrayAdaptor;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanisouakrim.newvision.Model.Film.Film;
import com.yanisouakrim.newvision.R;

import java.util.ArrayList;
import java.util.List;

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
       /* TextView tv_Nom = (TextView)layoutItem.findViewById(R.id.nom);
        TextView tv_Prenom = (TextView)layoutItem.findViewById(R.id.activite);
        //tv_Prenom.setText("cc");
        Log.v("DHEebug...",tv_Prenom.getText()+"");

        //(3) : Renseignement des valeurs
        tv_Nom.setText(this.objets.get(position).getNom());
        tv_Prenom.setText(this.objets.get(position).getActivite());*/

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
