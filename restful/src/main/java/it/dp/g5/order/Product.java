package it.dp.g5.order;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int ID;
    private String name;
    private float cost;
    private List<String> ingredientsList = new ArrayList<>();

    public Product(int ID) {
        this.ID = ID;
    }

    public Product(int ID, String name, float cost) {
        this.ID = ID;
        this.cost = cost;
    }

    public String getNome() {
        return name;
    }

    public void setNome(String nome) {
        this.name = nome;
    }

    public int getID() {
        return ID;
    }

    public List<String> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public String toString() {
        String stringa;
        if (!ingredientsList.isEmpty()) {
            stringa = getNome().toUpperCase() + ": ";
            stringa = ingredientsList.stream().map(entry -> entry + ", ").reduce(stringa, String::concat);
        } else {
            stringa = getNome().toUpperCase() + ": ";
        }
        return stringa;
    }
}
