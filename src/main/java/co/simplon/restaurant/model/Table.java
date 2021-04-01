package co.simplon.restaurant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Table {
    //Attributs
    private int idTable;
    private String name;
    private int nbPlaces;

    //Constructeur
    public Table(int idTable, String name, int nbPlaces){
        this.idTable = idTable;
        this.name = name;
        this.nbPlaces = nbPlaces;
    }

    //Fonction To String
    @Override
    public String toString() {
        return this.idTable + "- " + this.name;
    }

    //Fonction Lister les tables
    public static List<Table> listTables(Connection connect) throws SQLException {
        List<Table> listTables = new ArrayList<>();
        Statement statement = connect.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM tables");

        while(result.next()){
            Table tempTable = new Table(result.getInt("id_table"),result.getString("nom"),result.getInt("nb_convives"));
            listTables.add(tempTable);

            System.out.println(tempTable.toString());
        }
        System.out.println("Choisissez une table:");

        result.close();
        statement.close();

        return listTables;
    }
}
