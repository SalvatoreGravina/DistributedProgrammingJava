package it.dp.g5.order;

import java.util.HashMap;
import java.util.Map;

public class Product {

    private int ID;
    private String name;
    private float cost;
    private Map<Integer, String> ingredientsMap = new HashMap<>();

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

    public void setIngredients(Map<Integer, String> ingredientsMap) {
        this.ingredientsMap = ingredientsMap;
    }

    public int getID() {
        return ID;
    }

    public Map<Integer, String> getIngredients() {
        return ingredientsMap;
    }

    @Override
    public String toString() {
        String stringa = getNome().toUpperCase() + ": ";
        stringa += ingredientsMap.values().toString();
        return stringa;
    }
}
