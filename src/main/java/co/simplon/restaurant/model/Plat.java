package co.simplon.restaurant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    //Fonction To String
    @Override
    public String toString() {
        return this.idPlat + "- " + this.dishName;
    }

    //Fonction Lister les plats
    public static List<Plat> listPlats(Connection connect) throws SQLException {
        List<Plat> listPlats = new ArrayList<>();
        Statement statement = connect.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM plats");

        while(result.next()){
            Plat tempPlat = new Plat(result.getInt("id_plat"),result.getString("nom"),result.getDouble("prix_unitaire"));
            listPlats.add(tempPlat);

            System.out.println(tempPlat.toString());
        }
        System.err.println("0- Fin de saisie");
        System.out.println("##########################");

        result.close();
        statement.close();

        return listPlats;
    }

    public static void savePlat(Connection connect, int actualInvoice, int inputIdPlat, int inputQuantity) throws SQLException{
        Statement statement = connect.createStatement();
        String sql = "INSERT INTO plats_facture (id_facture, id_plat, quantite) VALUES (" + actualInvoice + ", " + inputIdPlat + ", " + inputQuantity + ")";
        System.out.println(sql);
        statement.execute(sql);
    }

}
