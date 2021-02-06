package com.example.moviestar.Controllers;

import android.app.Application;
import android.net.Uri;

import com.example.moviestar.Model.Film;
import com.example.moviestar.Model.Utente;

import java.util.ArrayList;

public class CurrentUser extends Application {
    private static String username;
    private static String userId;
    private static ArrayList<Utente> listaAmici;
    private static ArrayList<Utente> listaUtenti;
    private static ArrayList<Utente> listaRichiesteAmico;
    private static ArrayList<Utente> userList_DB;

    public static String getUsernameById_userlist_DB(String id_daCercare){
        for(int i=0; i< userList_DB.size(); i++){
            if(userList_DB.get(i).getIdUtente().equals(id_daCercare))
                return userList_DB.get(i).getNomeUtenteMostrato();
        }
        return "";
    }

    public static boolean checkPresenzaListaAmici(String idCercato){
        for(int i=0; i< listaAmici.size(); i++){
            if(listaAmici.get(i).getIdUtente().equals(idCercato))
                return true;
        }
        return false;
    }

    public static ArrayList<Utente> getUserList_DB() {
        return userList_DB;
    }

    public static void setUserList_DB(ArrayList<Utente> userList_DB) {
        CurrentUser.userList_DB = userList_DB;
    }

    public static ArrayList<Utente> getListaRichiesteAmico() {
        return listaRichiesteAmico;
    }

    public static void setListaRichiesteAmico(ArrayList<Utente> listaRichiesteAmico) {
        CurrentUser.listaRichiesteAmico = listaRichiesteAmico;
    }

    public static boolean daVedereContainsFilm(Film filmDaCercare){
        boolean result=false;
        String id = filmDaCercare.getId();
        for(int i=0; i< getListaFilmDaVedere().size(); i++) {
            if(!id.equals(getListaFilmDaVedere().get(i).getId()))
                result = false;
            else{
                result=true;
                break;
            }
        }
        return result;
    }

    public static boolean vistiContainsFilm(Film filmDaCercare){
        boolean result=false;
        String id = filmDaCercare.getId();
        for(int i=0; i< listaFilmVisti.size(); i++) {
            if(!id.equals(getListaFilmVisti().get(i).getId()))
                result = false;
            else{
                result=true;
                break;
            }
        }
        return result;
    }

    public static boolean preferitiContainsFilm(Film filmDaCercare){
        boolean result=false;
        String id = filmDaCercare.getId();
        for(int i=0; i< getListaFilmPreferiti().size(); i++) {
            if(!id.equals(getListaFilmPreferiti().get(i).getId()))
                result = false;
            else{
                result=true;
                break;
            }
        }
        return result;
    }


    public int lengthOfSpecifiedListFilm(ArrayList<Film> lista){
        return lista.size();
    }

    public static boolean aggiungiAmicoToUtente(Utente amicoDaAggiungere){
        return listaAmici.add(amicoDaAggiungere);
    }

    public static ArrayList<Utente> getListaAmici() {
        return listaAmici;
    }

    public static void setListaAmici(ArrayList<Utente> listaAmici) {
        CurrentUser.listaAmici = listaAmici;
    }

    public static ArrayList<Film> getListaFilmPreferiti() {
        return listaFilmPreferiti;
    }

    public static void setListaFilmPreferiti(ArrayList<Film> listaFilmPreferiti) {
        CurrentUser.listaFilmPreferiti = listaFilmPreferiti;
    }

    public static ArrayList<Film> getListaFilmDaVedere() {
        return listaFilmDaVedere;
    }

    public static void setListaFilmDaVedere(ArrayList<Film> listaFilmDaVedere) {
        CurrentUser.listaFilmDaVedere = listaFilmDaVedere;
    }

    private static ArrayList<Film> listaFilmVisti;
    private static ArrayList<Film> listaFilmPreferiti;
    private static ArrayList<Film> listaFilmDaVedere;

    public boolean addToSpecifiedListFilm(Film filmDaAggiungere, ArrayList<Film> listaInCuiAggiungere){
        return listaInCuiAggiungere.add(filmDaAggiungere);
    }

    public static ArrayList<Film> getListaFilmVisti() {
        return listaFilmVisti;
    }

    public static void setListaFilmVisti(ArrayList<Film> listaFilmVisti) {
        CurrentUser.listaFilmVisti = listaFilmVisti;
    }

    private static Uri imageUri;

    public static Uri getImageUri() {
        return imageUri;
    }

    public static void setImageUri(Uri imageUri) {
        CurrentUser.imageUri = imageUri;
    }

    private static CurrentUser instance;

    public static CurrentUser getInstance() {
        if (instance == null)
            instance = new CurrentUser();
        return instance;

    }

    public CurrentUser(){}


    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static void setListaUtenti(ArrayList<Utente> userList) {
        CurrentUser.listaUtenti = userList;
    }

    public static ArrayList<Utente> getListaUtenti(){
        return CurrentUser.listaUtenti;
    }
}
