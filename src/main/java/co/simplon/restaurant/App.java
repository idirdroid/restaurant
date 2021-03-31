package co.simplon.restaurant;

import co.simplon.restaurant.model.Menu;
import co.simplon.restaurant.model.Plat;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //Lancement programme

        //Boolean pour savoir si le programme s'arrête
        boolean running = true;
        //Scanner pour la saisie utilisateur
        Scanner scan = new Scanner(System.in);


        //Informations de connexion à la BDD
        String url = "jdbc:postgresql://localhost:5432/database_restaurant";
        String user = "postgres";
        String password = "postgres";

        try {
            //Connection à la BDD
            Connection connection = DriverManager.getConnection(url, user, password);

            //Affichage du menu tant que l'utilisateur ne quitte pas le programme
            do {
                //On affiche le menu de l'application de puis la BDD
                //A revoir car obligé de créer un objet
                Menu menu = new Menu(0,"");
                menu.showMenu(connection);

                //Saisie utilisateur dans userInput
                int userInput;
                userInput = scan.nextInt();
                scan.nextLine();


                //Execution des commande selon la saisie utilisateur
                switch (userInput) {
                    case 1:
                        saveInvoice(scan, connection);
                        break;
                    case 2:
                        bestTable(connection);
                        break;
                    case 3:
                        bestPlat(connection);
                        break;
                    default: {
                        running = false;
                        break;
                    }
                }
            } while (running);

            connection.close();

        } catch (SQLException throwables) {
            System.err.println("Problème de connexion à la BDD");
            //throwables.printStackTrace();
        }



        System.out.println("Fin du programme");
    }

    private static void bestPlat(Connection connect) {
        System.out.println("Plat avec le meilleur chiffre d'affaire:");

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connect.createStatement();
            String sql = "select p.nom as nom_plat, SUM(p.prix_unitaire * pf.quantite) as chiffre_affaire from factures f\n" +
                    "join plats_facture pf on f.id_facture = pf.id_facture\n" +
                    "join plats p on p.id_plat = pf.id_plat\n" +
                    "join tables t on t.id_table = f.id_table\n" +
                    "--WHERE s.nom LIKE 'Simon'\n" +
                    "GROUP BY p.nom\n" +
                    "ORDER BY chiffre_affaire DESC";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("nom_plat") + " - " + resultSet.getString("chiffre_affaire"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void bestTable(Connection connect) {
        System.out.println("Tables avec le meilleur chiffre d'affaire:");

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connect.createStatement();
            String sql = "select t.nom, SUM(p.prix_unitaire * pf.quantite) as chiffre_affaire from factures f\n" +
                    "join plats_facture pf on f.id_facture = pf.id_facture\n" +
                    "join plats p on p.id_plat = pf.id_plat\n" +
                    "join serveurs s on s.id_serveur = f.id_serveur\n" +
                    "join tables t on t.id_table = f.id_table\n" +
                    "GROUP BY t.nom\n" +
                    "ORDER BY chiffre_affaire DESC;";

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("nom") + " - " + resultSet.getString("chiffre_affaire"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void saveInvoice(Scanner scan, Connection connect) {
        System.out.println("Saisir une facture:");
        System.out.println("##################################");


        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connect.createStatement();

            //Affichage des tables
            resultSet = statement.executeQuery("SELECT * from tables ORDER BY id_table");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id_table") + "- " + resultSet.getString("nom"));
            }
            System.out.println("Choisissez une table:");
            int tableChoice = scan.nextInt();
            scan.nextLine();
            //Affichage de serveurs
            resultSet = statement.executeQuery("SELECT * from serveurs ORDER BY id_serveur");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id_serveur") + "- " + resultSet.getString("prenom") + " " + resultSet.getString("nom"));
            }
            System.out.println("Choisissez un.e serveur.se:");
            int serveurChoice = scan.nextInt();
            scan.nextLine();

            //Création de la facture avec le numéro de Table et le numéro de serveur
            String sql = "INSERT INTO factures (id_table, id_serveur) VALUES (" + tableChoice +"," + serveurChoice + ");";
            System.out.println(sql);
            statement.execute(sql);

            //Récupération de l'identifiant de la facture
            resultSet = statement.executeQuery("select currval('factures_id_facture_seq') as id");
            resultSet.next();
            int actualInvoice = resultSet.getInt("id");

            System.out.println("id Facture : " + actualInvoice);


            //Enregistrement des plat de la facture courant
            //Affichage des plats
            resultSet = statement.executeQuery("SELECT * from plats ORDER BY id_plat");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("id_plat") + "- " + resultSet.getString("nom"));
            }
            System.err.println("0- Fin de saisie");
            System.out.println("##########################");

            //Enregistrement des plats (saisie utilisateur, stockage dans une HashMap
            boolean userInput = true;
            do{
                System.out.println("Choisissez un plat");
                int inputIdPlat = scan.nextInt();
                scan.nextLine();
                if(inputIdPlat != 0){
                    System.out.println("Quelle quantité");
                    int inputQuantity = scan.nextInt();
                    scan.nextLine();

                    //Enregistrement du plat pour la facture en cours
                    sql = "INSERT INTO plats_facture (id_facture, id_plat, quantite) VALUES (" + actualInvoice + ", " + inputIdPlat + ", " + inputQuantity + ")";
                    System.out.println(sql);
                    statement.execute(sql);
                }
                else {
                    userInput = false;
                }

            } while (userInput);


            resultSet.close();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
