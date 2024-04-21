package com.example.demo.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import UtillsMADAMIRA.APIExampleUse;
import UtillsMADAMIRA.Helper;
import com.example.demo.model.QuerySearch;
import com.example.demo.model.Taffsir;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    private final static String SOLR_URL = "http://localhost:8984/solr/mainCore";
    public ArrayList<QuerySearch> querySolr(String query) {

        SolrClient solrClient = new HttpSolrClient.Builder(SOLR_URL).build();
        ArrayList<QuerySearch>allData=new ArrayList<>();
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setParam("fl","soura","Texte","Nom","An","NomS","id");
        QueryResponse response;
        try {
            response = solrClient.query(solrQuery);
            for(SolrDocument re:response.getResults()){
                allData.add(new QuerySearch((String) re.get("soura"), Helper.filterText((String) re.get("Texte")),(String) re.get("Nom"),(String) re.get("An"),(String) re.get("NomS"),(String)re.get("id")));
            }
            return allData;
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public HashSet<Taffsir> getSouras(String query) {
        Helper.writeToXml("xml/SampleInputFile.xml",query);
        APIExampleUse.lemme();
        HashSet<Taffsir> souras = new HashSet<>();
        SolrClient solrClient = new HttpSolrClient.Builder(SOLR_URL).build();
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setParam("fl","NomS","An","Nom");
        solrQuery.setRows(58981);
        QueryResponse response;
        try {
            response = solrClient.query(solrQuery);
            for(SolrDocument re:response.getResults()){
                souras.add(new Taffsir((String) re.get("NomS"),(String) re.get("An"),(String) re.get("Nom")));
            }
            System.out.println(souras.size());
            return souras;
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
