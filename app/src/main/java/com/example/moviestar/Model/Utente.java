package com.example.moviestar.Model;

import java.io.File;
import java.util.ArrayList;

public class Utente {
    String idUtente;
    String nomeUtenteMostrato;
    String password;
    String emailUtente;
    boolean isVerified;
    File immagineProfilo;
    ArrayList<Film> filmPreferiti;
    ArrayList<Film> filmDaVedere;
    ArrayList<Film> filmVisti;
    ArrayList<Utente> listaAmici;

    public Utente(String idUtente, String nomeUtenteMostrato, File immagineProfilo, ArrayList<Utente> listaAmici) {
        this.idUtente = idUtente;
        this.nomeUtenteMostrato = nomeUtenteMostrato;
        this.immagineProfilo = immagineProfilo;
        this.listaAmici = listaAmici;
    }

    public Utente(String idUtente, String nomeUtenteMostrato, String emailUtente, boolean isVerified, File immagineProfilo, ArrayList<Film> filmPreferiti, ArrayList<Film> filmDaVedere, ArrayList<Film> filmVisti, ArrayList<Utente> listaAmici) {
        this.idUtente = idUtente;
        this.nomeUtenteMostrato = nomeUtenteMostrato;
        this.emailUtente = emailUtente;
        this.isVerified = isVerified;
        this.immagineProfilo = immagineProfilo;
        this.filmPreferiti = filmPreferiti;
        this.filmDaVedere = filmDaVedere;
        this.filmVisti = filmVisti;
        this.listaAmici = listaAmici;
    }

    public Utente(String idUtente, String password1, String email) {
    }

    public Utente(String idUtente, String password) {
        this.idUtente=idUtente;
        this.password=password;
    }

    public String getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(String idUtente) {
        this.idUtente = idUtente;
    }

    public String getNomeUtenteMostrato() {
        return nomeUtenteMostrato;
    }

    public void setNomeUtenteMostrato(String nomeUtenteMostrato) {
        this.nomeUtenteMostrato = nomeUtenteMostrato;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public File getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(File immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    public ArrayList<Film> getFilmPreferiti() {
        return filmPreferiti;
    }

    public void setFilmPreferiti(ArrayList<Film> filmPreferiti) {
        this.filmPreferiti = filmPreferiti;
    }

    public ArrayList<Film> getFilmDaVedere() {
        return filmDaVedere;
    }

    public void setFilmDaVedere(ArrayList<Film> filmDaVedere) {
        this.filmDaVedere = filmDaVedere;
    }

    public ArrayList<Film> getFilmVisti() {
        return filmVisti;
    }

    public void setFilmVisti(ArrayList<Film> filmVisti) {
        this.filmVisti = filmVisti;
    }

    public ArrayList<Utente> getListaAmici() {
        return listaAmici;
    }

    public void setListaAmici(ArrayList<Utente> listaAmici) {
        this.listaAmici = listaAmici;
    }

    public String getPassword() {
        return this.password;
    }
}
