package it.dp.g5.javamail;

import it.dp.g5.exception.JavaMailException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Classe che fornisce i metodi statici per l'invio di mail
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class JavaMailUtils {

    /**
     * Invia una mail
     *
     * @param recepient mail del destinatario
     * @param name nome del creatore dell'ordine
     * @param orderID ID dell'ordine da mandare via mail
     * @throws it.dp.g5.exception.JavaMailException errore invio mail ad utente
     */
    public static void sendMail(String recepient, String name, int orderID) throws JavaMailException {
        try {
            System.out.println("Preparing to send email");
            Properties properties = new Properties();

            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            String myAccountEmail = "PizzeriaDiem@gmail.com";
            String password = "PizzaDiem2020";

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, password);
                }
            });

            Message message = prepareMessage(session, myAccountEmail, recepient, name, orderID);

            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException ex) {
            throw new JavaMailException();
        }
    }

    /**
     * Prepara il testo della mail da inviare
     *
     * @param session sessione con il mail server
     * @param myAccountEmail account email del mittente
     * @param recepient account email del destinatario
     * @param name nome del detinatario
     * @param orderID ID dell'ordine da notificare
     */
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String name, int orderID) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Pizzeria DP - ordine ricevuto");
            String htmlCode = "<h1>Ordine confermato: </h1> <br/> <h2>" + name + ", il tuo ordine numero " + orderID + " è stato ricevuto </h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
        }
        return null;
    }

}
