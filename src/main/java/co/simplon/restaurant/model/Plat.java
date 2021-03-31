package co.simplon.restaurant.model;

public class Plat {
    private int idPlat;
    private String dishName;
    private double price;

    public double getPrice() {
        return price;
    }

    public int getId() {
        return idPlat;
    }

    public String getDishName() {
        return dishName;
    }
}
