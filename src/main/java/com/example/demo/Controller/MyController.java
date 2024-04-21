package com.example.demo.Controller;

import UtillsMADAMIRA.APIExampleUse;
import UtillsMADAMIRA.Helper;
import com.example.demo.Service.FileService;
import com.example.demo.model.QuerySearch;
import com.example.demo.model.Taffsir;
import org.apache.solr.common.SolrDocument;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MyController {
    private final FileService fileService;

    public MyController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/search")
    public ArrayList<QuerySearch> searchSolr(@RequestParam String query) {
       // String nv= Helper.trnsformQuery(query);
        return fileService.querySolr(query);
    }
    @GetMapping("/searchb")
    public HashSet<Taffsir> searchSolrB(@RequestParam String query) {
        System.out.println(query);
        return fileService.getSouras(query);
    }
    @GetMapping("/searchA")
    public ArrayList<QuerySearch> search(@RequestParam String query) {
        return fileService.querySolr(query);
    }
    @GetMapping("/mada")
    public void mada(@RequestParam String query){
         Helper.writeToXml("xml/SampleInputFile.xml",query);
        APIExampleUse.lemme();
    }
}
