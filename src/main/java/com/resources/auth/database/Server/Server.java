package com.resources.auth.database.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tanvd on 30.11.15.
 */
public class Server implements Serializable{
    static Integer idInc = 0;

    private Integer id = 0;

    private  String ip;

    private String format;

    public Server() { idInc += 1; }

    public Server(String ip, String format) {
        this.id = idInc;
        idInc += 1;
        this.ip = ip;
        this.format = format;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void sendLogin(String login) throws IOException
    {
        String url = "http://" + ip + "/" + format.replace("{login}", login);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        System.err.println("INFOCUSTOM: Executing request");
        int responseCode = con.getResponseCode();
        System.err.println("INFOCUSTOM: Sending 'GET' request to URL : " + url);
        System.err.println("INFOCUSTOM: Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.err.println("INFOCUSTOM: Response: " + response.toString());
    }
}
