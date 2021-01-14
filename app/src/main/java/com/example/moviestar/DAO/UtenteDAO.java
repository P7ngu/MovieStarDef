package com.example.moviestar.DAO;

public class UtenteDAO {
    String imageURI;
    String currentID;

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getCurrentID() {
        return currentID;
    }

    public void setCurrentID(String currentID) {
        this.currentID = currentID;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    String currentUsername;

    public UtenteDAO(String imageURI, String currentID, String currentUsername) {
        this.imageURI = imageURI;
        this.currentID = currentID;
        this.currentUsername = currentUsername;
    }

    public UtenteDAO(){}

    public static boolean checkEmailNonPresente(String email) {
        return false;
    }

    public static boolean checkIdUtenteNonPresente(String idUtente) {
        return false;
    }
}
