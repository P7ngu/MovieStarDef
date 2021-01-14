package com.example.moviestar.Model;

public class Commento {
    String testo;
    String nomeAutore;

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
