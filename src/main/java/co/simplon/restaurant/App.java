package co.simplon.restaurant;

import co.simplon.restaurant.model.*;

import java.sql.*;
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
                //On affiche le menu de l'application depuis la BDD
                Menu.showMenu(connection);

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
                    case 4:
                        Plat.listDish(connection, false);
                        break;
                    case 5:
                        Plat.addDish(scan, connection);
                        break;
                    case 6:
                        Plat.updateDish(scan, connection);
                        break;
                    case 7:
                        Plat.removeDish(scan, connection);
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
            //Requête SQL
            String sql = "select t.nom, SUM(p.prix_unitaire * pf.quantite) as chiffre_affaire from factures f\n" +
                    "join plats_facture pf on f.id_facture = pf.id_facture\n" +
                    "join plats p on p.id_plat = pf.id_plat\n" +
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

        try {
            //Affichage des tables
            Table.listTables(connect);
            int tableChoice = scan.nextInt();
            scan.nextLine();

            //Affichage de serveurs
            Serveur.listServeur(connect);
            int serveurChoice = scan.nextInt();
            scan.nextLine();

            //Création de la facture avec l'ID de Table et l'Id de serveur
            //Méthode saveFacture dans la classe facture qui renvoi l'id de la facture créée
            int actualInvoice = Facture.saveFacture(connect, tableChoice, serveurChoice);

            //Enregistrement des plats (saisie utilisateur)
            boolean userInput = true;
            do{
                //Affichage des plats à chaque saisie - Arrêt en tapant 0
                Plat.listDish(connect, true);

                System.out.println("Choisissez un plat");
                int inputIdPlat = scan.nextInt();
                scan.nextLine();
                //Si la saisie est égale à 0 alors arrêt de la saisie
                if(inputIdPlat != 0){
                    System.out.println("Quantité consommée:");
                    int inputQuantity = scan.nextInt();
                    scan.nextLine();

                    //Enregistrement du plat pour la facture en cours
                    Plat.saveInvoiceDish(connect,actualInvoice, inputIdPlat, inputQuantity);
                }
                else {
                    //Sinon arrêt de la saisie
                    userInput = false;
                }

            } while (userInput);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
