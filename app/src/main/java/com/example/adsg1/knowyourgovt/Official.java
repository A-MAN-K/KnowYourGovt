package com.example.adsg1.knowyourgovt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adsg1 on 4/6/2017.
 */
public class Official implements Serializable {

    String officialName;
    String officialDesignation;
    String party;
    String addLineOne;
    String addLineTwo;
    String city;
    String state;

    String eMail;
    String webSite;

    String zipCode;
    String phone;
    String urls;
    String photoUrls;
    HashMap<String,String> channels;


    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Official(String city, String state, String zipCode) {
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Official(String officialDesignation) {
        this.officialDesignation = officialDesignation;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public static int getCtr() {
        return ctr;
    }

    public static void setCtr(int ctr) {
        Official.ctr = ctr;
    }

    private static int ctr =1;

 /*   public Official() {

        this.officialName = "A_Man"+ ctr;
        this.officialDesignation = "President";
        ctr++;
    }
*/

 public Official(){}


    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getOfficialDesignation() {
        return officialDesignation;
    }

    public void setOfficialDesignation(String officialDesignation) {
        this.officialDesignation = officialDesignation;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getAddLineOne() {
        return addLineOne;
    }

    public void setAddLineOne(String addLineOne) {
        this.addLineOne = addLineOne;
    }

    public String getAddLineTwo() {
        return addLineTwo;
    }

    public void setAddLineTwo(String addLineTwo) {
        this.addLineTwo = addLineTwo;
    }

    public String getCity() {
        return city;
    }

    public HashMap<String, String> getChannels() {
        return channels;
    }

    public void setChannels(HashMap<String, String> channels) {
        this.channels = channels;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
