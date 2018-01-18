package fr.m2i.webservicesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText etCode;
    TextView tvPays;
    ListView listePays;
    String ADR_ONE = "http://demo@services.groupkt.com/country/get/iso2code/";
    String ADR_ALL = "http://demo@services.groupkt.com/country/get/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void searchOne(View v){
        etCode = findViewById(R.id.txtCode);
        tvPays = findViewById(R.id.txtNom);
        String jsonText="";
        System.out.println("LA reponse arrivé JSONObject");
        String code = etCode.getText().toString();

            GetUrl getUrl = new GetUrl();
            getUrl.setAdr(ADR_ONE + code);
            getUrl.start();  //Lancer le traitement dans le thread séparé
        try {
            getUrl.join();   //Attendre la fin du traitement
            jsonText=getUrl.getResponse();
            Toast.makeText(this, getUrl.getResponse(), Toast.LENGTH_LONG).show();
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            JSONObject json = new JSONObject(jsonText);
            JSONObject rest = json.getJSONObject("RestResponse");
            JSONObject result = rest.getJSONObject("result");
            String name = result.getString("name");
            tvPays.setText(name);
        }
        catch (Exception ex) {
         Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    public void searchAll (View v) throws JSONException {
        String jsonText="";
        System.out.println("LA reponse arrivé JSONObject");

        GetUrl getUrl = new GetUrl();
        getUrl.setAdr(ADR_ALL);
        getUrl.start();  //Lancer le traitement dans le thread séparé
        try {
            getUrl.join();   //Attendre la fin du traitement
            jsonText=getUrl.getResponse();
            Toast.makeText(this, getUrl.getResponse(), Toast.LENGTH_LONG).show();
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            JSONObject json = new JSONObject(jsonText);
            JSONObject restResponse = json.getJSONObject("RestResponse"); // on accède dans le premier objet (première accolade { )
            JSONArray result = restResponse.getJSONArray("result");   // On accède à l'objet result qui sera un tableau

            ArrayList<String> listNom = new ArrayList<String>(); // creation du tableau pour y mettre la liste des pays

            //boucle d'ajout des noms dans un tableau
            for (int i = 0; i < result.length(); i++) {
                //recherche objet par objet dans le tableau result
                JSONObject pays = result.getJSONObject(i);   //on récupère l'objet à l'indice i
                //ajout le "nom" de chaque objet parcouru dans le tableau listNom
                listNom.add(pays.getString("name"));
            }

            //affichage du tableau dans la ListView (listeView permet d'afficher les liste d'objet sous forme de tableau (xml)
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listNom);
            listePays = findViewById(R.id.lstPays);
            listePays.setAdapter(adapter);
        }
        catch (Exception ex) {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    /*
    public void test2(){
        Intent i = new Intent();
    }
    */
}