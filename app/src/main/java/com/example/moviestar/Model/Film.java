package com.example.moviestar.Model;

public class Film {

    String id;
    String name;
    String img;
    String vote;
    String overview;

    public Film(){

    }

    public Film(String id, String name, String img, String vote, String overview) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.vote="Voto medio: "+vote;
        this.overview=overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = "Voto medio: "+ vote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
