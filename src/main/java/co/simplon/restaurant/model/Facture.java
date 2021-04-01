package co.simplon.restaurant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Facture {
    private int idFacture;
    private int idTable;
    private int idServeur;

    //constructeur
    public Facture(int idFacture, int idTable, int idServeur){
        this.idFacture = idFacture;
        this.idTable = idTable;
        this.idServeur = idServeur;
    }

    public static int saveFacture(Connection connect, int idtable, int idServeur) throws SQLException {
        int idFacture;

        Statement statement = connect.createStatement();
        String sql = "INSERT INTO factures (id_table, id_serveur) VALUES (" + idtable +"," + idServeur + ");";
        System.out.println(sql);
        statement.execute(sql);

        //Récupération de l'identifiant de la facture
        ResultSet result = statement.executeQuery("select currval('factures_id_facture_seq') as id");
        result.next();
        idFacture = result.getInt("id");

        System.out.println("id Facture : " + idFacture);

        return idFacture;
    }
}
