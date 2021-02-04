package com.example.moviestar.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.Controllers.ModificaRecensioneController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.Controllers.RecensioniFilmController;
import com.example.moviestar.Model.Commento;
import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoPreferitoActivity;
import com.example.moviestar.View.home.RecyclerCommenti.AdapteryCommenti;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecensioneDAO {
    static int number_star=0;

    //COMMENTI



    public static void eliminaRecensione_Firebase(Commento commentoDaRimuovere, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection("RichiesteAmico");
        String filmId=commentoDaRimuovere.getFilmId();
        String seconds=commentoDaRimuovere.getTimeStamp();
        db.collection("Commenti").document(userId+filmId+seconds)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        PopupController.mostraPopup("Commento rimosso", "Commento rimosso con successo", mContext);
                        //REFRESH SCHERMATA
                        ModificaRecensioneController.setRisultato(true);
                        RecensioniFilmController.onClickLeggiCommenti(filmId, mContext);
                        LoginController.loadCurrentUserDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", "non rimosso, errore di sistema", mContext);
                    }
                });
    }
   //OVERLOAD
    public static void eliminaRecensione_Firebase(Commento commentoDaRimuovere, Context mContext, RecyclerView recyclerView){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection("RichiesteAmico");
        String filmId=commentoDaRimuovere.getFilmId();
        String seconds=commentoDaRimuovere.getTimeStamp();
        db.collection("Commenti").document(userId+filmId+seconds)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        PopupController.mostraPopup("Commento rimosso", "Commento rimosso con successo", mContext);
                        //REFRESH SCHERMATA
                       ModificaRecensioneController.setRisultato(true);
                        RecensioniFilmController.onClickLeggiCommenti(filmId, mContext, recyclerView);
                        LoginController.loadCurrentUserDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", "non rimosso, errore di sistema", mContext);
                    }
                });
    }




    //VOTO

    public static void riempiStelle_Firebase(int numeroStelleDaRiempire, String filmId) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection("VotoStar");

        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("userID", userId);
        data4.put("voto", numeroStelleDaRiempire );
        filmPreferiti.document(userId+filmId).set(data4);
    }


    public static int getNumberOfStars_Firebase(String filmId, int number_star1) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String path="VotoStar";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection(path);

        db.collection(path)
                .whereEqualTo("userID", userId).whereEqualTo("filmID", filmId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                number_star= Integer.parseInt(String.valueOf(document.get("voto")));
                                try {
                                    MostraDettagliFilmVistoCliccatoPreferitoActivity.riempiStelle(number_star, false);
                                    MostraDettagliFilmVistoCliccatoActivity.riempiStelle(number_star, false);
                                    Log.d("DebugStelle", number_star+"");
                                } catch (Exception e){
                                    try{
                                        MostraDettagliFilmVistoCliccatoActivity.riempiStelle(number_star, false);
                                        Log.d("DebugStelle", number_star+"");}
                                    catch (Exception e1){

                                    }
                                }
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());

                    }
                });

        return number_star;
    }

    public static void getListaCommentiFilm_Firebase(String idFilm, Context mContext, RecyclerView recyclerView) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String path = "Commenti";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference commentiCollection = db.collection(path);
        ArrayList<Commento> commentiList = new ArrayList<>();

        db.collection(path)
                .whereEqualTo("filmID", idFilm)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //data = document.getData();
                                String commentoText = document.getData().get("commento").toString();
                                String userid = document.getData().get("userID").toString();
                                String username = document.getData().get("username").toString();
                                String seconds = document.getData().get("unique_number").toString();
                                String idFilm=document.getData().get("filmID").toString();
                                Commento commentoTemp = new Commento(username, userid, commentoText, seconds, idFilm);

                                if (commentoTemp != null) commentiList.add(commentoTemp);
                            }
                            PutDataIntoRecyclerView(commentiList, recyclerView, mContext);
                        } else
                            Log.d("testFirebase", "Error getting documents: ", task.getException());
                    }
                });
    }

    private static void PutDataIntoRecyclerView(List<Commento> commentoList, RecyclerView commentiRecycler, Context mContext){
        AdapteryCommenti adaptery=new AdapteryCommenti(mContext, commentoList, commentiRecycler);
        commentiRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        commentiRecycler.setAdapter(adaptery);

    }

    public static void inserisciCommentoFilm_Firebase(String idFilmCommentato, String commentoDaInserire, Context mContext) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String username = currentUser.getUsername();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference richiesteamico = db.collection("Commenti");
        int seconds = Math.toIntExact(Timestamp.now().getSeconds());

        Map<String, Object> data4 = new HashMap<>();
        data4.put("userID", userId);
        data4.put("commento", commentoDaInserire);
        data4.put("filmID", idFilmCommentato);
        data4.put("username", username);
        data4.put("unique_number", seconds);
        richiesteamico.document(userId + idFilmCommentato + seconds).set(data4);

        LoginController.loadCurrentUserDetails();
        PopupController.mostraPopup("Commento inviato", "Commento inviato con successo!", mContext);
        RecensioniFilmController.openListaCommentiDopoInserimentoCommento(idFilmCommentato, mContext);
    }
}
