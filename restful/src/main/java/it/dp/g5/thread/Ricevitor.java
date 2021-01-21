/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.thread;

import it.dp.g5.backend.OrderManager;

/**
 *
 * @author gruppo 5
 */
 public class Ricevitor implements Runnable{
    @Override
    public void run(){
        try {
            OrderManager manager = OrderManager.getInstance();
            while (true) {
                Thread.sleep(15000);
                String[] results = manager.popOrder();
                System.out.println("Servito ordine per la sala N°" + results[0] + " in arrivo da: " + results[1]);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
    }
