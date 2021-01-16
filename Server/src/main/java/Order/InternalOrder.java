
package Order;

import java.time.LocalDateTime;


public class InternalOrder extends Order{
    
    private int table;
    private int sitting;

    public InternalOrder(int table, int sitting, LocalDateTime date, float cost) {
        super(date, cost);
        this.table = table;
        this.sitting = sitting;
    }
    
    
    
    public int getTavolo() {
        return table;
    }

    public int getCoperti() {
        return sitting;
    }
    
    
    
}
