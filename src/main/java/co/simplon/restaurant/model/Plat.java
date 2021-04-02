package co.simplon.restaurant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Plat {
    private int idPlat;
    private String dishName;
    private double price;

    //Constructeur
    public Plat(int idPlat, String name, double price){
        this.idPlat = idPlat;
        this.dishName = name;
        this.price = price;
    }

    //Méthode To String
    @Override
    public String toString() {
        return this.idPlat + "- " + String.format("%1$-30s", this.dishName) + this.price;

    }

    //Méthode Lister les plats
    public static List<Plat> listDish(Connection connect, boolean insert) throws SQLException {
        List<Plat> listPlats = new ArrayList<>();
        Statement statement = connect.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM plats ORDER BY id_plat ASC");

        //On parcours le résultat de la requête pour en créer des objets
        while(result.next()){
            Plat tempPlat = new Plat(result.getInt("id_plat"), result.getString("nom"),result.getDouble("prix_unitaire"));
            listPlats.add(tempPlat);

            System.out.println(tempPlat.toString());
        }
        //On gère l'affichage de la dernière ligne en cas d'ajout de plat à la facture
        if(insert){
            System.err.println("0- Fin de saisie");
        }
        System.out.println("##########################");

        result.close();
        statement.close();

        return listPlats;
    }

    //Fonction enregistrement d'un plat pour une facture
    public static void saveInvoiceDish(Connection connect, int actualInvoice, int inputIdPlat, int inputQuantity) throws SQLException{
        Statement statement = connect.createStatement();
        String sql = "INSERT INTO plats_facture (id_facture, id_plat, quantite) VALUES (" + actualInvoice + ", " + inputIdPlat + ", " + inputQuantity + ")";
        //DEBUG -- System.out.println(sql);
        statement.execute(sql);
    }

    //Méthode d'ajout d'un plat dans la table
    public static void addDish(Scanner scan, Connection connect) throws SQLException {
        //Demande saisie utilisateur
        System.out.println("Renseignez les informations du plat svp (Intitulé et prix unitaire)");
        System.out.println("Intitulé: ");
        String dish = scan.nextLine();
        System.out.println("Prix Unitaire: ");
        double price = scan.nextDouble();
        scan.nextLine();

        Statement statement = connect.createStatement();
        try {
            //Requête d'enregistrement
            statement.execute("INSERT INTO plats (nom, prix_unitaire) VALUES ('"+ dish +"','"+ price +"')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        statement.close();
    }

    //Méthode de mise à jour d'un plat dans la table
    public static void updateDish(Scanner scan, Connection connect) throws SQLException {
        //Demande saisie utilisateur
        System.out.println("Quel plat voulez vous modifier?");
        listDish(connect, false);
        int choice = scan.nextInt();
        scan.nextLine();

        System.out.println("Entrez le nouvel intitulé:");
        String intitule = scan.nextLine();

        System.out.println("Entrez le nouveau prix:");
        double price = scan.nextDouble();
        scan.nextLine();

        Statement statement = connect.createStatement();
        try {
            //Requête d'enregistrement
            statement.execute("UPDATE plats SET nom = '"+ intitule +"', prix_unitaire = '"+ price +"' WHERE id_plat = '"+ choice +"'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        statement.close();
    }

    //Méthode de suppression d'un plat dans la table
    public static void removeDish(Scanner scan, Connection connect) throws SQLException {
        //Demande saisie utilisateur
        System.out.println("Quel plat voulez vous supprimer?");
        listDish(connect, false);
        int choice = scan.nextInt();
        scan.nextLine();

        Statement statement = connect.createStatement();
        try {
            //Requête d'enregistrement
            statement.execute("DELETE FROM plats WHERE id_plat = "+ choice);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        statement.close();
    }

}
