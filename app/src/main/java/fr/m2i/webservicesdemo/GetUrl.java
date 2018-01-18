package fr.m2i.webservicesdemo;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetUrl extends Thread {
    private String adr;
    public String getAdr(){return adr;}
    public void setAdr(String adr){this.adr=adr;}

    private String response;
    public String getResponse(){return response;}
   // public void setResponse(String response) {this.response = response;}     Inutile car non utilisée

    public void run(){
        URL url = null;
        response="";
        HttpURLConnection cnt = null;
        try {
            url = new URL(getAdr());
            cnt = (HttpURLConnection) url.openConnection();
            InputStream stream = cnt.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response += line;
            }
            stream.close();
            reader.close();
        } catch (Exception ex) {
            response += "\nErreur : " + ex.getMessage();
        } finally {
            cnt.disconnect();
        }
    }
}


