
package Ordine;

import java.time.LocalDateTime;


public class OrdineSala extends Ordine{
    
    private int tavolo;
    private int coperti;

    public OrdineSala(int tavolo, int coperti, int ID, LocalDateTime data, float importo) {
        super(ID, data, importo);
        this.tavolo = tavolo;
        this.coperti = coperti;
    }
    
    
    
    public int getTavolo() {
        return tavolo;
    }

    public int getCoperti() {
        return coperti;
    }
    
    
    
}
