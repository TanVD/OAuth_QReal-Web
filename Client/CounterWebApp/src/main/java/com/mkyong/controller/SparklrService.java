package com.mkyong.controller;


import java.net.URI;
import org.springframework.web.client.RestOperations;

public class SparklrService {

    private String ourUserServersUrl;
    private RestOperations sparklrRestTemplate;

    public String getLoginMy() {
        return this.sparklrRestTemplate.getForObject(URI.create(ourUserServersUrl), String.class);
    }

    public void setOurUserServersUrl(String ourUserServersUrl) {
        this.ourUserServersUrl = ourUserServersUrl;
    }

    public void setSparklrRestTemplate(RestOperations sparklrRestTemplate) {
        this.sparklrRestTemplate = sparklrRestTemplate;
    }
}