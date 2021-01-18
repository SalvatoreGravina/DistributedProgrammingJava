
package it.dp.g5.order;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class InternalOrder extends Order{
    
    private int table;
    private int sitting;

    public InternalOrder(int table, int sitting, Timestamp date) {
        super(date);
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
