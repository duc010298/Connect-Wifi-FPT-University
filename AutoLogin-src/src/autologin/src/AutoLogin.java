/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autologin.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Đỗ Trung Đức
 */
public class AutoLogin {

    /**
     * @param url
     * @throws org.apache.http.client.ClientProtocolException
     */
    public static void basicPost(String url) throws ClientProtocolException, IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("auth_user", "hnfdl-161020-600"));
            params.add(new BasicNameValuePair("auth_pass", "h274600"));
            params.add(new BasicNameValuePair("accept", "true"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(httpPost);
        }
    }

    public static void main(String[] args) {
        System.out.println("Running");
        try {
            basicPost("http://192.168.22.1:8002/index.php?zone=zone");
        } catch (IOException ex) {
            Logger.getLogger(AutoLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Done");
    }

}
