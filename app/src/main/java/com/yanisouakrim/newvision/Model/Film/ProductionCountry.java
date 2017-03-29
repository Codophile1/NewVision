package com.yanisouakrim.newvision.Model.Film;

/**
 * Created by willi on 22/03/2017.
 */

public class ProductionCountry
{
    private String iso_3166_1;
    private String name;

    public ProductionCountry(String iso_3166_1, String name) {
        this.iso_3166_1 = iso_3166_1;
        this.name = name;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getName() {
        return name;
    }
}
