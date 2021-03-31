package co.simplon.restaurant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Menu {
    private int id;
    private String title;

    public Menu (int id, String title){
        this.id = id;
        this.title = title;
    }

    public void showMenu(Connection connect){
        //Requête SQL
        //ArrayList<Menu> listMenu = new ArrayList<Menu>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connect.createStatement();
            String sql = "select * FROM menu";
            resultSet = statement.executeQuery(sql);

            //On laisse un espace pour réafficher le menu
            System.out.println("#######################################");
            System.out.println();
            System.out.println("Menu Restaurant:");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id_menu") + "- " + resultSet.getString("title"));
            }
//On laisse un espace pour réafficher le menu
            System.out.println("#######################################");
            System.out.println();

            resultSet.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
