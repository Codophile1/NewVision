package com.yanisouakrim.newvision.Model.Film;

/**
 * Created by willi on 22/03/2017.
 */

public class ProductionCompany
{
    private int id;
    private String name;

    public ProductionCompany(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
