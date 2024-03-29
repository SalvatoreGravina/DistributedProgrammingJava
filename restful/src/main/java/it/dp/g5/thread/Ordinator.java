package it.dp.g5.thread;

import it.dp.g5.order.TakeAwayOrder;
import it.dp.g5.order.FriedProduct;
import it.dp.g5.order.InternalOrder;
import it.dp.g5.order.DeliveryOrder;
import it.dp.g5.order.PizzaProduct;
import it.dp.g5.backend.OrderManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.jms.JMSException;

/**
 * Classe che gestice la comunicazione con il DB
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 * @deprecated
 */
public class Ordinator {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws InterruptedException, JMSException {
//        String json = null;
//        Map<String, Integer> mappa = null;
//
//        List<String> ingredientsList = new ArrayList<>();
//        ingredientsList.add("value1");
//        ingredientsList.add("Value2");
//        ingredientsList.add("Value3");
//
//        PizzaProduct pizza = new PizzaProduct(0);
//        pizza.setNome("margherita");
//        pizza.setIngredientsList(ingredientsList);
//
//        PizzaProduct pizza1 = new PizzaProduct(1);
//        pizza1.setNome("capricciosa");
//        pizza1.setIngredientsList(ingredientsList);
//
//        FriedProduct frittatina = new FriedProduct(2);
//        frittatina.setNome("frittatina pasta e patate");
//        frittatina.setIngredientsList(ingredientsList);
//
////        DeliveryOrder ordineDelivery = new DeliveryOrder("ciccio@gmail.com", "Ciccio", Timestamp.valueOf(LocalDateTime.now()), "viagiggino", "333", Timestamp.valueOf(LocalDateTime.now()));
////        ordineDelivery.addProduct(pizza, 5);
////        ordineDelivery.addProduct(pizza1, 2);
////        ordineDelivery.addProduct(frittatina, 100);
//
////        InternalOrder ordineSala = new InternalOrder(1, 5, LocalDateTime.now(), 100);
////        ordineSala.addProduct(pizza, 5);
////        ordineSala.addProduct(pizza1, 2);
////        ordineSala.addProduct(frittatina, 100);
////
////        TakeAwayOrder ordineAsporto = new TakeAwayOrder("ciccio", LocalDateTime.now(), 100);
////        ordineAsporto.addProduct(pizza, 5);
////        ordineAsporto.addProduct(pizza1, 2);
////        ordineAsporto.addProduct(frittatina, 100);
//
//        OrderManager manager = OrderManager.getInstance();
//        Random rand = new Random();
//        Thread t1 = new Thread (new Ricevitor());
//        t1.start();
//        int i = 0;
//        while (true) {
//            //ordineAsporto.setID(i);
//            //manager.pushOrder(ordineAsporto);
//            //i++;
//            //Thread.sleep(10000 + rand.nextInt(10000));
//            //ordineSala.setID(i);
//            //manager.pushOrder(ordineSala);
//            //i++;
//            //Thread.sleep(10000 + rand.nextInt(10000));
//            ordineDelivery.setID(i);
//            manager.pushOrder(ordineDelivery);
//            i++;
//            Thread.sleep(10000 + rand.nextInt(10000));
//        }
//
//    }
//    
//    public static class Ricevitor implements Runnable{
//    @Override
//    public void run(){
//        try {
//            OrderManager manager = OrderManager.getInstance();
//            while (true) {
//                Thread.sleep(15000);
//                String[] results = manager.popOrder();
//                System.out.println("Servito ordine per la sala N°" + results[0] + " in arrivo da: " + results[1]);
//            }
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//
//    }
//    }

}
