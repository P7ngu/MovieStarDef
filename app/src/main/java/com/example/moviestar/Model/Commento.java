package com.example.moviestar.Model;

public class Commento {
    String testo;
    String nomeAutore;
    String idAutore;
    String filmId;

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public Commento(String username, String userId, String commentoText, String seconds, String idFilm) {
        this.nomeAutore=username;
        this.testo=commentoText;
        this.idAutore=userId;
        this.timeStamp=seconds;
        this.filmId=idFilm;
    }

    public String getIdAutore() {
        return idAutore;
    }

    public void setIdAutore(String idAutore) {
        this.idAutore = idAutore;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    String timeStamp;

    public Commento(String username, String userId, String commentoText) {
        this.nomeAutore=username;
        this.testo=commentoText;
        this.idAutore=userId;
    }

    public Commento(String username, String userId, String commentoText, String seconds) {
        this.nomeAutore=username;
        this.testo=commentoText;
        this.idAutore=userId;
        this.timeStamp=seconds;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getNomeAutore() {
        return nomeAutore;
    }

    public void setNomeAutore(String nomeAutore) {
        this.nomeAutore = nomeAutore;
    }

    public Commento(String testo, String nomeAutore) {
        this.testo = testo;
        this.nomeAutore = nomeAutore;
    }
}
