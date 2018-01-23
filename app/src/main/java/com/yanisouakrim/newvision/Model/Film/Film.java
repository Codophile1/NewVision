package com.yanisouakrim.newvision.Model.Film;

import java.util.ArrayList;

/**
 * Created by willi on 22/03/2017.
 */

public class Film
{
    private String id;
    private String title;
    private boolean adult;
    private CollectionFilm collection;
    private ArrayList<Genre> genres;
    private String homePage;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private String popularity;
    private String posterPath;
    private ArrayList<ProductionCompany> productionCompanies;
    private ArrayList<ProductionCountry> production_countries;
    private String release_date;
    private String status;
    private String tagLine;
    private String voteAverage;
    private int voteCount;

    public Film(String id, String title,  String release_date,String posterPath) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.posterPath=posterPath;
    }

    public Film(String id, String title, boolean adult, CollectionFilm collection, ArrayList<Genre> genres,
                String homePage, String originalLanguage, String originalTitle, String overview, String popularity,
                String posterPath, ArrayList<ProductionCompany> productionCompanies,
                ArrayList<ProductionCountry> production_countries, String release_date,
                String status, String tagLine, String voteAverage, int voteCount) {
        this.id = id;
        this.title = title;
        this.adult = adult;
        this.collection = collection;
        this.genres = genres;
        this.homePage = homePage;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.productionCompanies = productionCompanies;
        this.production_countries = production_countries;
        this.release_date = release_date;
        this.status = status;
        this.tagLine = tagLine;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAdult() {
        return adult;
    }

    public CollectionFilm getCollection() {
        return collection;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public String getHomePage() {
        return homePage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public ArrayList<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public ArrayList<ProductionCountry> getProduction_countries() {
        return production_countries;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getStatus() {
        return status;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
