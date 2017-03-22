package com.yanisouakrim.newvision.Model.Film;

/**
 * Created by willi on 22/03/2017.
 */

public class CollectionFilm
{
    private int id;
    private String name;
    private String poster_path;

    public CollectionFilm(int id, String name, String poster_path) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
