package Ordine;

import java.util.HashMap;
import java.util.Map;

public class Prodotto {

    int ID;
    private String nome;
    private String tipo;
    private Map<Integer, String> ingredienti = new HashMap<>();

    public Prodotto(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setIngredienti(Map<Integer, String> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public int getID() {
        return ID;
    }

    public String getTipo() {
        return tipo;
    }

    public Map<Integer, String> getIngredienti() {
        return ingredienti;
    }

    @Override
    public String toString() {
        String stringa = getNome().toUpperCase() + ": ";     
        for(String entry : ingredienti.values()){
            stringa += entry + ", ";
        }
        return stringa;
    }

}
