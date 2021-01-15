package JMS;


import Order.DeliveryOrder;
import Order.FriedProduct;
import Order.PizzaProduct;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.jms.JMSException;

/**
 *
 * @author gruppo 5
 */
public class Sender {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String json = null;
        Map<String,Integer> mappa=null;
        Map<Integer, String> elements = new HashMap();
        elements.put(1, "Value1");
        elements.put(2, "Value2");
        elements.put(3, "Value3");
        
        GregorianCalendar deliveryTime = new GregorianCalendar(Locale.ITALY);
        deliveryTime.set(2021, 0, 14, 19, 30);
        
        DeliveryOrder ordine = new DeliveryOrder("ciccio@gmail.com", "Ciccio", deliveryTime,"viagiggino", "333", LocalDateTime.now(), (float) 10.10);
        ordine.setID(0);
        
        PizzaProduct pizza = new PizzaProduct(0);
        pizza.setNome("margherita");
        
        pizza.setIngredients(elements);
        ordine.addProduct(pizza, 5);
        
        PizzaProduct pizza1 = new PizzaProduct(1);
        pizza1.setNome("capricciosa");
        
        pizza1.setIngredients(elements);
        ordine.addProduct(pizza1, 2);
        
        FriedProduct frittatina = new FriedProduct(2);
        frittatina.setNome("frittatina pasta e patate");
        
        frittatina.setIngredients(elements);
        ordine.addProduct(frittatina, 100);

    }

}
