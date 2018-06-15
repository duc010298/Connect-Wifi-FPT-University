/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Đỗ Trung Đức
 */
public class MyUtils {

    public String getURL() {
        final String USER_AGENT = "Mozilla/5.0";
        final String url = "http://msftconnecttest.com/redirect";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setConnectTimeout(5000);
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                if (testConnect()) {
                    JOptionPane.showMessageDialog(null, "Your network is not the network of the FPT dormitory or you are already logged on to the network");
                    return null;
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot connect to Internet");
                    return null;
                }
            }
            String conString = con.toString();
            if (!conString.contains("zone=zone")) {
                JOptionPane.showMessageDialog(null, "Your network is not the network of the FPT dormitory or you are already logged on to the network");
                return null;
            }
            int index = conString.indexOf("zone=zone");
            String ret = conString.substring(44, index + 9);
            return ret;
        } catch (HeadlessException | IOException ex) {
            if (testConnect()) {
                JOptionPane.showMessageDialog(null, "Your network is not the network of the FPT dormitory or you are already logged on to the network");
                return null;
            } else {
                JOptionPane.showMessageDialog(null, "Cannot connect to Internet");
                return null;
            }
        }
    }

    private boolean testConnect() {
        final String USER_AGENT = "Mozilla/5.0";
        final String url = "https://www.google.com/";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setConnectTimeout(5000);
            int responseCode = con.getResponseCode();
            return responseCode == 200;
        } catch (HeadlessException | IOException ex) {
            return false;
        }
    }

    public boolean sendRequest(String URL, String username, String password) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(URL);
            ArrayList<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("auth_user", username));
            params.add(new BasicNameValuePair("auth_pass", password));
            params.add(new BasicNameValuePair("accept", "true"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            client.execute(httpPost);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public int getSleepTime(String URL) {
        boolean isDormitory;
        boolean haveInternet;
        final String USER_AGENT = "Mozilla/5.0";
        final String url = URL;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setConnectTimeout(5000);
            int responseCode = con.getResponseCode();
            isDormitory = (responseCode == 200);
            haveInternet = testConnect();
        } catch (HeadlessException | IOException ex) {
            isDormitory = false;
            haveInternet = testConnect();
        }
        if(isDormitory && haveInternet) {
            return 15000;
        }
        if(isDormitory && !haveInternet) {
            return 100;
        }
        if(!isDormitory && haveInternet) {
            return 60000;
        }
        if(!isDormitory && !haveInternet) {
            return 200;
        }
        return 60000;
    }

}
