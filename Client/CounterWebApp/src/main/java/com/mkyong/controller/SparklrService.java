package com.mkyong.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestOperations;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SparklrService {

    private String sparklrPhotoListURL;
    private String sparklrTrustedMessageURL;
    private String sparklrPhotoURLPattern;


    public void setTrustedMessageURL(String trustedMessageURL) {
        this.trustedMessageURL = trustedMessageURL;
    }

    private String trustedMessageURL;
    private String ourUserServersUrl;
    private RestOperations sparklrRestTemplate;
    private RestOperations trustedClientRestTemplate;

    public String getLoginMy() {
        return this.sparklrRestTemplate.getForObject(URI.create(ourUserServersUrl), String.class);
    }

    public String getTrustedMessage() {
        return this.trustedClientRestTemplate.getForObject(URI.create(trustedMessageURL), String.class);
    }

    public void setOurUserServersUrl(String ourUserServersUrl) {
        this.ourUserServersUrl = ourUserServersUrl;
    }

    public void setSparklrPhotoURLPattern(String sparklrPhotoURLPattern) {
        this.sparklrPhotoURLPattern = sparklrPhotoURLPattern;
    }

    public void setSparklrPhotoListURL(String sparklrPhotoListURL) {
        this.sparklrPhotoListURL = sparklrPhotoListURL;
    }

    public void setSparklrTrustedMessageURL(String sparklrTrustedMessageURL) {
        this.sparklrTrustedMessageURL = sparklrTrustedMessageURL;
    }

    public void setSparklrRestTemplate(RestOperations sparklrRestTemplate) {
        this.sparklrRestTemplate = sparklrRestTemplate;
    }

    public void setTrustedClientRestTemplate(RestOperations trustedClientRestTemplate) {
        this.trustedClientRestTemplate = trustedClientRestTemplate;
    }

}