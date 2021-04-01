package co.simplon.restaurant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Serveur {
    private int idServeur;
    private String nom;
    private String prenom;

    //Constructeur
    public Serveur(int idServeur, String name, String prenom){
        this.idServeur = idServeur;
        this.nom = name;
        this.prenom = prenom;
    }

    //Fonction To String
    @Override
    public String toString() {
        return this.idServeur + "- " + this.nom;
    }

    //Fonction Lister les tables
    public static List<Serveur> listServeur(Connection connect) throws SQLException {
        List<Serveur> listServeurs = new ArrayList<>();
        Statement statement = connect.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM serveurs");

        while(result.next()){
            Serveur tempServeur = new Serveur(result.getInt("id_serveur"),result.getString("nom"),result.getString("prenom"));
            listServeurs.add(tempServeur);

            System.out.println(tempServeur.toString());
        }
        System.out.println("Choisissez un.e serveur.se:");

        result.close();
        statement.close();

        return listServeurs;
    }
}
