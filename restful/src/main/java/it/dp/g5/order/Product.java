package it.dp.g5.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un prodotto generico
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class Product {

    private int ID;
    private String name;
    private float cost;
    private List<String> ingredientsList = new ArrayList<>();

    /**
     * Costruttore della classe Product
     *
     * @param ID identificativo del prodotto
     */
    public Product(int ID) {
        this.ID = ID;
    }

    /**
     * Costruttore della classe Product
     *
     * @param ID identificativo del prodotto
     * @param name nome del prodotto
     * @param cost costo del prodotto
     */
    public Product(int ID, String name, float cost) {
        this.ID = ID;
        this.cost = cost;
    }

    /**
     *
     *
     * @return nome del prodotto
     */
    public String getNome() {
        return name;
    }

    /**
     *
     * @param nome nome del prodotto
     */
    public void setNome(String nome) {
        this.name = nome;
    }

    /**
     *
     * @return identificativo dell'ordine
     */
    public int getID() {
        return ID;
    }

    /**
     *
     * @return collection degli ingredienti
     */
    public List<String> getIngredientsList() {
        return ingredientsList;
    }

    /**
     *
     * @param ingredientsList collection degli ingredienti
     */
    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    /**
     *
     * @return fornisce informazioni complete sul prodotto (nome, ingredienti e
     * quantità)
     */
    @Override
    public String toString() {
        String stringa;
        if (!ingredientsList.isEmpty()) {
            stringa = getNome().toUpperCase() + ": ";
            stringa += ingredientsList;
        } else {
            stringa = getNome().toUpperCase() + ": ";
        }
        return stringa;
    }
}
