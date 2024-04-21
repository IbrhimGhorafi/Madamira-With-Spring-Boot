package UtillsMADAMIRA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class Helper {
    public static ArrayList<String> genererFichierLemme(String fileName) {
        try {

            File file = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            StringBuilder sb;
            ArrayList<String> lemmes;
            String target;
            System.out.println("processing : " + file.getName());
            doc = builder.parse(file);
            lemmes = getLemmes(doc.getElementsByTagName("svm_prediction"));
            return lemmes;

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;


    }
    public static ArrayList<String> getLemmes(NodeList nodes) {
        ArrayList<String> lemmas = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) (nodes.item(i).getFirstChild());
            String lemma = filterText(element.getAttribute("lemma"));
            //if (!lemmas.contains(lemma)) {
            lemmas.add(lemma);
            //}
        }
        return lemmas;
    }
    public static String filterText(String input) {
        return input.replaceAll("[^\\p{InArabic}]", " ").replaceAll("[\\.?,;:،؟؛]", " ").replaceAll("\\s+", " ").trim();
    }
    public static void writeToXml(String fileName, String content) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            StringBuffer fileContent = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><madamira_input xmlns=\"urn:edu.columbia.ccls.madamira.configuration:0.1\"><madamira_configuration><preprocessing sentence_ids=\"false\" separate_punct=\"true\" input_encoding=\"UTF8\"/><overall_vars output_encoding=\"UTF8\" dialect=\"MSA\" output_analyses=\"TOP\" morph_backoff=\"NONE\"/><requested_output> <req_variable name=\"LEMMA\" value=\"true\" /></requested_output></madamira_configuration><in_doc id=\"ExampleDocument\"><in_seg id=\"SENT1\">")
                    .append(content).append("</in_seg></in_doc></madamira_input>");
            fileWriter.write(fileContent.toString());
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String trnsformQuery(String query){
        String queryy;
       // StringBuilder nouvrquet=new StringBuilder();
       // String nvQuery="Texte:* "+query.replaceAll(" "," *   ").concat(" *");
        queryy="Aya4:\""+query+"\" OR Aya2:\""+query+"\" OR Texte:\""+query+"\"";
        System.out.println(queryy);
        return queryy;
    }


}
