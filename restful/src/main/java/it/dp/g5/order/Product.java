package it.dp.g5.order;

import java.util.HashMap;
import java.util.Map;

public class Product {

    private int ID;
    private String nome;
    private Map<Integer, String> ingredientsMap = new HashMap<>();

    public Product(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
