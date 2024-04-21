package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.example.demo",exclude = SolrAutoConfiguration.class)
public class DemoApplication {
    
    public static void main(String[] args) {

      SpringApplication.run(DemoApplication.class, args);

    }

}
