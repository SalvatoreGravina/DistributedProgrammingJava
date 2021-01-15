 
package orderService;

import Order.Order;
import Order.ExternalOrder;
import Order.InternalOrder;
import java.time.LocalDateTime;
import java.util.List;


public class OrderDAO {
    
    
    public List<Order> getAllOrders() {
        
        return TestUtils.prendi();
    }
    
    public boolean addOrder(String email, boolean type, String name, String deliveryAddress, String phone) {
        ExternalOrder externalOrder = new ExternalOrder(email, type, name, LocalDateTime.now(), deliveryAddress, phone, 100, LocalDateTime.now(), (float) 29.99);
        return TestUtils.aggiungi(externalOrder);
    }
    
    public boolean addOrder(int table, int sitting) {
        InternalOrder internalOrder = new InternalOrder(table, sitting, LocalDateTime.now().getMinute(), LocalDateTime.now(), (float) 49.99);
        return TestUtils.aggiungi(internalOrder);
    }
    
    public boolean modifyOrder(String email, boolean type, String name, String deliveryAddress, String phone, int ID) {
        ExternalOrder externalOrder = new ExternalOrder(email, type, name, LocalDateTime.now(), deliveryAddress, phone, ID, LocalDateTime.now(), (float) 29.99);
        return TestUtils.cambia(ID, externalOrder);
    }
    
    public boolean modifyOrder(int table, int sitting, int ID) {
        InternalOrder internalOrder = new InternalOrder(table, sitting, ID, LocalDateTime.now(), (float) 49.99);
        return TestUtils.cambia(ID, internalOrder);
    }
    
    public boolean deleteOrder(int ID) {
        Order o = TestUtils.trova(ID);
        System.err.println(o);
        if (o!= null){
            return TestUtils.cancella(o);
        }
        return false;
    }
}
