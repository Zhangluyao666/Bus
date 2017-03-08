package com.zxzq.bus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/2/17 0017.
 */
public class NetConnManager {
    public static String loadJson (String url) {
        System.out.println("44444: "+url);
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            System.out.println("URL:"+url.toString());
            HttpURLConnection uc = (HttpURLConnection)urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        return json.toString();
    }


}
