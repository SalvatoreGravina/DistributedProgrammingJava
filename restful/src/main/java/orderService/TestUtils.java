
package orderService;

import OrderOld.ExternalOrder;
import OrderOld.Order;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class TestUtils {
    
    private static ArrayList<Order> orderList = new ArrayList<>();
    
    public static boolean aggiungi(Order order) {
        return orderList.add(order);
    }
    
    
    public static ArrayList<Order> prendi() {
        return orderList;
    }
    
    public static Order trova (int ID) {
        for (Order o: orderList){
            if(o.getID() == ID) {
                return o;
            }
        }
        return null;
    }
    
    public static boolean cambia (int ID, Order o) {
        for (Order i: orderList){
            if(i.getID() == ID) {
                orderList.remove(i);
                orderList.add(o);
                return true;
            }
        }
        return false;
    }
    
    public static boolean cancella(Order order) {
        return orderList.remove(order);
    }

    
}
