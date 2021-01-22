package it.dp.g5.pushnotification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 * Classe che fornisce i metodi statici per mandare notifiche push ad un
 * determinato device.
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class FCMNotification {

    public final static String AUTH_KEY_FCM = "AAAAhwlg8DM:APA91bG3nkIKSzPaz8NrXcYPMldnHj3IAIg_QwSd7ceqMUwSaTL2G4m2Zh8uCyAC7FS81yhNyO_JxYWMbHARHpFVWXmHrvCqOEbIUs8cuy7Zl1h_N_DqxoOuH4jvgkZ3fW6gy99Oj0oO";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    /**
     * Manda una notifica push
     *
     * @param DeviceIdKey TokenID univoco relativo al dispositivo android di target della notifica
     * @param title Titolo della notifica
     * @param text Corpo della notifica
     * @throws java.lang.Exception errore durante l'invio della notifica
     */
    public static void pushFCMNotification(String DeviceIdKey, String title, String text) throws Exception {

        String authKey = AUTH_KEY_FCM;      // Authentication key del server FireCloud
        String FMCurl = API_URL_FCM;        // API per l'invio di notifiche

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject data = new JSONObject();
        data.put("to", DeviceIdKey.trim());
        JSONObject info = new JSONObject();
        info.put("title", title); 
        info.put("text", text);
        data.put("notification", info);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data.toString());
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

    }
}
