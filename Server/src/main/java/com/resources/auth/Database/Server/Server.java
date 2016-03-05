package com.resources.auth.Database.Server;

import javax.annotation.Resource;
import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is the class of server.
 * This class also used as entity in database.
 * @author TanVD
 */

@Entity
@Table(name = "SERVERS")
public class Server implements Serializable{

    public enum State {
        StandBy,
        WaitingForAnswer,
        Error,
        ConnectionEstablished;
    }

    static Integer idInc = 0;

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id = 0;


    @Column(name = "Name")
    private String name;

    @Column(name = "Ip")
    private  String ip;

    @Column(name = "Format")
    private String format;

    @Transient
    private HttpURLConnection connection;

    public Server() {
        idInc++;
    }

    @Transient
    private State connectionState = State.StandBy;

    public Server(String ip, String format, String name) {
        this.id = idInc;
        idInc += 1;
        this.ip = ip;
        this.format = format;
        this.name = name;
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

    public State getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(State connectionState) {
        this.connectionState = connectionState;
    }

    public int getConnectionStateForJsp(){
        if (connectionState == State.StandBy)
        {
            return 1;
        }
        else if (connectionState == State.WaitingForAnswer)
        {
            return 2;
        }
        else if (connectionState == State.Error)
        {
            return 3;
        }
        else if (connectionState == State.ConnectionEstablished)
        {
            return 4;
        }
        return 3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This function is used to broadcast new users credentials to server.
     * It opens HTTP connection to server and broadcast via GET request
     * new user to REST API of server. Will be used REST API template
     * from server's field.
     * @throws IOException In case of error or in case of not replying for 10 secs exception will be thrown.
     */
    public void sendLogin(String login) throws IOException
    {
        String url = "http://" + ip + "/" + format.replace("{login}", login);

        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        System.err.println("INFOCUSTOM: Executing request");
        connection.setConnectTimeout(10000);
        try {
            int responseCode = connection.getResponseCode();
            connectionState = State.ConnectionEstablished;
            System.err.println("INFOCUSTOM: Sending 'GET' request to URL : " + url);
            System.err.println("INFOCUSTOM: Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.err.println("INFOCUSTOM: Response: " + response.toString());
        }
        catch (java.net.SocketTimeoutException e)
        {
            connectionState = State.Error;
            System.err.println("INFOCUSTOM: Server not responding. 5 sec timeout " + url);
        }


    }
}
