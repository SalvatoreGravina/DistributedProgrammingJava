package Thread;

import Backend.OrderManager;
import Order.*;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.jms.JMSException;

/**
 *
 * @author gruppo 5
 */
public class Ordinator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, JMSException {
        String json = null;
        Map<String, Integer> mappa = null;

        Map<Integer, String> elements = new HashMap();
        elements.put(1, "Value1");
        elements.put(2, "Value2");
        elements.put(3, "Value3");

        PizzaProduct pizza = new PizzaProduct(0);
        pizza.setNome("margherita");
        pizza.setIngredients(elements);

        PizzaProduct pizza1 = new PizzaProduct(1);
        pizza1.setNome("capricciosa");
        pizza1.setIngredients(elements);

        FriedProduct frittatina = new FriedProduct(2);
        frittatina.setNome("frittatina pasta e patate");
        frittatina.setIngredients(elements);

        GregorianCalendar deliveryTime = new GregorianCalendar(Locale.ITALY);
        deliveryTime.set(2021, 0, 14, 19, 30);

        DeliveryOrder ordineDelivery = new DeliveryOrder("ciccio@gmail.com", "Ciccio", deliveryTime, "viagiggino", "333", LocalDateTime.now(), (float) 10.10);
        ordineDelivery.addProduct(pizza, 5);
        ordineDelivery.addProduct(pizza1, 2);
        ordineDelivery.addProduct(frittatina, 100);

        InternalOrder ordineSala = new InternalOrder(1, 5, LocalDateTime.now(), 100);
        ordineSala.addProduct(pizza, 5);
        ordineSala.addProduct(pizza1, 2);
        ordineSala.addProduct(frittatina, 100);

        TakeAwayOrder ordineAsporto = new TakeAwayOrder("ciccio", LocalDateTime.now(), 100);
        ordineAsporto.addProduct(pizza, 5);
        ordineAsporto.addProduct(pizza1, 2);
        ordineAsporto.addProduct(frittatina, 100);

        OrderManager manager = OrderManager.getInstance();
        Random rand = new Random();
        Thread t1 = new Thread (new Ricevitor());
        t1.start();
        int i = 0;
        while (true) {
            ordineAsporto.setID(i);
            manager.pushOrder(ordineAsporto);
            i++;
            Thread.sleep(10000 + rand.nextInt(10000));
            ordineSala.setID(i);
            manager.pushOrder(ordineSala);
            i++;
            Thread.sleep(10000 + rand.nextInt(10000));
            ordineDelivery.setID(i);
            manager.pushOrder(ordineDelivery);
            i++;
            Thread.sleep(10000 + rand.nextInt(10000));
        }

    }
    
    public static class Ricevitor implements Runnable{
    @Override
    public void run(){
        try {
            OrderManager manager = OrderManager.getInstance();
            while (true) {
                Thread.sleep(15000);
                String[] results = manager.popOrder();
                System.out.println("Servito ordine per la sala N°" + results[0] + " in arrivo da: " + results[1]);
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}

}
