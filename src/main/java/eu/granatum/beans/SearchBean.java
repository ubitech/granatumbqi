/*
* Panagiotis Hasapis, Panagiotis Gouvas, Danai Vergeti
*
* Copyright (c) 2012 UBITECH Ltd. All Rights Reserved.
*
* This software is the confidential and proprietary information of UBITECH Ltd.
*
* UBITECH MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
* THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
* TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
* PARTICULAR PURPOSE, OR NON-INFRINGEMENT. UBITECH SHALL NOT BE LIABLE FOR
* ANY DAMAGES SUFFERED AS A RESULT OF USING, MODIFYING OR
* DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/



package eu.granatum.beans;

import eu.granatum.xsd.Sparql;
import eu.granatum.xsd.Sparql.Results.Result;
import java.io.BufferedInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

/**
 * Created with IntelliJ IDEA.
 * User: pgouvas
 * Date: 7/23/12
 * Time: 4:35 PM
 */
@ManagedBean(name = "SearchBean")
@RequestScoped
public class SearchBean {

    /**
     * Creates a new instance of SearchBean
     */
    @ManagedProperty(value = "#{param.annotations}")    
    private String annotations;
    private String drugs;
    int chemoLength = 0;
    int molLength = 0;
    int drugsLength = 0;
    private List<Sparql.Results.Result> allResults;
    private List<Sparql.Results.Result> documentResults;
    private List<Sparql.Results.Result> imageResults;
    private List<Sparql.Results.Result> forumResults;
    private List<Sparql.Results.Result> clinicalResults;
    private List<Sparql.Results.Result> drugResults;
    private List<Sparql.Results.Result> chemoResults;
    private List<Sparql.Results.Result> molResults;    
   
    final Logger logger = LoggerFactory.getLogger(SearchBean.class);

    @ManagedProperty(value = "#{param.searchterm}")
    private String searchterm;    

    public String getSearchterm() {
        return searchterm;
    }

    public void setSearchterm(String searchterm) {
        this.searchterm = searchterm;
    }

    
    public SearchBean() {
    }
/*
    public String getPassedParameters()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.searchterm  = (String) facesContext.getExternalContext().getRequestParameterMap().get("searchterm");
        this.annotations = (String) facesContext.getExternalContext().getRequestParameterMap().get("selection");
        return new String("test");
    }      
*/
    
    public List<Result> getMolResults() {
        return molResults;
    }

    public void setMolResults(List<Result> molResults) {
        this.molResults = molResults;
    }

    public int getMolLength() {
        return molLength;
    }

    public void setMolLength(int molLength) {
        this.molLength = molLength;
    }

    public int getDrugsLength() {
        return drugsLength;
    }

    public void setDrugsLength(int drugsLength) {
        this.drugsLength = drugsLength;
    }


    public List<Result> getChemoResults() {
        return chemoResults;
    }

    public void setChemoResults(List<Result> chemoResults) {
        this.chemoResults = chemoResults;
    }

    public int getChemoLength() {
        return this.chemoLength;
    }

    public void setChemoLength(int chemoLength) {
        this.chemoLength = chemoLength;
    }
    
    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public List<Sparql.Results.Result> getAllResults() {
        return allResults;
    }

    public void setAllResults(List<Sparql.Results.Result> allResults) {
        this.allResults = allResults;
    }



    public List<Sparql.Results.Result> getDocumentResults() {
        return documentResults;
    }

    public void setDocumentResults(List<Sparql.Results.Result> documentResults) {
        this.documentResults = documentResults;
    }


    public List<Sparql.Results.Result> getImageResults() {
        return imageResults;
    }

    public void setImageResults(List<Sparql.Results.Result> imageResults) {
        this.imageResults = imageResults;
    }

    public List<Sparql.Results.Result> getDrugResults() {
        return drugResults;
    }

    public void setDrugResults(List<Sparql.Results.Result> drugResults) {
        this.drugResults = drugResults;
    }

    public List<Result> getForumResults() {
        return forumResults;
    }

    public void setForumResults(List<Result> forumResults) {
        this.forumResults = forumResults;
    }

    public List<Result> getClinicalResults() {
        return clinicalResults;
    }

    public void setClinicalResults(List<Result> clinicalResults) {
        this.clinicalResults = clinicalResults;
    }



    public void doSearch(){
        //Actual Web Service Invocation
        //TODO otan i danai ksekoulathei
        String filename = null;
        setChemoResults(null);
        //mock Web Service Invocation
        try{
            logger.info( "Working path: "+new java.io.File(".").getCanonicalPath()  );
            System.out.println("Working path: "+new java.io.File(".").getCanonicalPath());

        } catch (Exception ex){
            logger.error(ex.toString());
        }


        if (annotations.trim().equalsIgnoreCase("0")){  //search1.xml
//            parseMockXMLFile("/home/ubiadmin/BQIfolder/sample1.xml");
            parseXMLFile("/home/ubiadmin/temp/BQIfolder/sample1.xml");            
        }
        
        if (annotations.trim().equalsIgnoreCase("1")){
            DataCloudSPARQLInterface d = new DataCloudSPARQLInterface("SELECT ?ChemoAgent ?Label ?smile ?sdf WHERE { ?ChemoAgent a <http://chem.deri.ie/granatum/ChemopreventiveAgent>. ?ChemoAgent <http://www.w3.org/2000/01/rdf-schema#label> ?Label. ?ChemoAgent <http://bio2rdf.org/ns/ns/ns/pubchem#SMILES> ?smile.?ChemoAgent <http://chem.deri.ie/granatum/sdf_file> ?sdf. filter regex(?Label,\""+ searchterm +"\",\"i\").}");
            try {                           
                filename = d.getAssociatedEntities();
                parseXMLFile(filename);
                File f = new File(filename);
                f.delete();           
                
            } catch (Throwable ex) {
                java.util.logging.Logger.getLogger(SearchBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (annotations.trim().equalsIgnoreCase("2")){
            DataCloudSPARQLInterface d = new DataCloudSPARQLInterface("SELECT ?mol ?label ?smile ?sameas WHERE { ?mol a <http://chem.deri.ie/granatum/Molecule>. ?mol <http://www.w3.org/2000/01/rdf-schema#label> ?label. ?mol <http://bio2rdf.org/ns/bio2rdf#smiles> ?smile. ?mol <http://bio2rdf.org/ns/bio2rdf#sameAs> ?sameas. filter regex(?label,\"" + searchterm + "\",\"i\").}");
            try {            
                filename = d.getAssociatedEntities();
                parseXMLFile(filename);
                File f = new File(filename);
                f.delete();           
            } catch (Throwable ex) {
                java.util.logging.Logger.getLogger(SearchBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        
        if (annotations.trim().equalsIgnoreCase("3")){
            DataCloudSPARQLInterface d = new DataCloudSPARQLInterface("SELECT ?Drug ?label WHERE { ?Drug a <http://chem.deri.ie/granatum/Drug>. ?Drug <http://www.w3.org/2000/01/rdf-schema#label> ?label. filter regex(?label,\""+searchterm+"\",\"i\").}");
            try {            
                filename = d.getAssociatedEntities();
                parseXMLFile(filename);
                File f = new File(filename);
                f.delete();                           
            } catch (Throwable ex) {
                java.util.logging.Logger.getLogger(SearchBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
    }

    private void parseXMLFile(String filename){
        try{
            JAXBContext jc = JAXBContext.newInstance( "eu.granatum.xsd" );
            Unmarshaller u = jc.createUnmarshaller();
            Object f = u.unmarshal( new File(filename ) );
            Sparql sparql =(Sparql) f;
            List<Object> list = sparql.getHeadOrResults();
            System.out.println("Test 1");
            for (int i = 0; i < list.size(); i++) {
                Object o =  list.get(i);
                if (o instanceof Sparql.Head){
                    Sparql.Head head = (Sparql.Head) o;

                }
                if (o instanceof Sparql.Results){
                    //set results
                    Sparql.Results results = (Sparql.Results) o;
                    allResults = results.getResult();
                    //Reset Lists
                    documentResults = new ArrayList<Sparql.Results.Result>();
                    imageResults = new ArrayList<Sparql.Results.Result>();
                    forumResults = new ArrayList<Sparql.Results.Result>();
                    clinicalResults = new ArrayList<Sparql.Results.Result>();
                    drugResults = new ArrayList<Sparql.Results.Result>();  
                    chemoResults = new ArrayList<Sparql.Results.Result>();
                    molResults = new ArrayList<Sparql.Results.Result>();
                    
                    for (int j = 0; j < allResults.size(); j++) {
                        System.out.println("Test 3");
                        Sparql.Results.Result res = allResults.get(j);
                        
                        Sparql.Results.Result.Binding binding = res.getBinding().get(0);
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/PublishedWork")) {
                            //add to document
                            documentResults.add(res);
                        }
                        
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/diagram2D")) {
                            //add them to images
                            imageResults.add(res);
                        }                        
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/ForumPost")) {
                            //add them to images
                            forumResults.add(res);
                        }                        
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/ClinicalTrial")) {
                            //add them to images
                            clinicalResults.add(res);
                        }
                        if (binding.getName().trim().startsWith("Drug")) {
                            //add them to drugs
                            drugResults.add(res);
                        }
                        if (binding.getName().trim().startsWith("ChemoAgent")) {
                            System.out.println("----" + res);
                            chemoResults.add(res);
                        }                        
                        if (binding.getName().trim().startsWith("mol")) {
                            molResults.add(res);
                        }                                                
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.chemoLength = chemoResults.size();
    }//EoM

    private void parseMockXMLFile(InputStream is){
        try{
            JAXBContext jc = JAXBContext.newInstance( "eu.granatum.xsd" );
            Unmarshaller u = jc.createUnmarshaller();
//            Object f = u.unmarshal( new File( "C:\\projects\\granatumbqi\\target\\test-classes\\"+filename ) );
//            Object f = u.unmarshal( new BufferedInputStream((is)) );
            Object f = u.unmarshal( new URL("http://srvgal78.deri.ie:8080/graph/Granatum/sparql?output=xml&query=SELECT%20%3FChemoAgent%20%3FLabel%20%3Fsmile%20%3Fsdf%20WHERE%20{%20%3FChemoAgent%20a%20%3Chttp%3A%2F%2Fchem.deri.ie%2Fgranatum%2FChemopreventiveAgent%3E.%20%3FChemoAgent%20%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23label%3E%20%3FLabel.%20%3FChemoAgent%20%3Chttp%3A%2F%2Fbio2rdf.org%2Fns%2Fns%2Fns%2Fpubchem%23SMILES%3E%20%3Fsmile.%3FChemoAgent%20%3Chttp%3A%2F%2Fchem.deri.ie%2Fgranatum%2Fsdf_file%3E%20%3Fsdf.%20filter%20regex%28%3FLabel%2C%22asp%22%2C%22i%22%29.}") );
            Sparql sparql =(Sparql) f;
            List<Object> list = sparql.getHeadOrResults();
            System.out.println("Test 1");
            for (int i = 0; i < list.size(); i++) {
                Object o =  list.get(i);
                if (o instanceof Sparql.Head){
                    Sparql.Head head = (Sparql.Head) o;
                    System.out.println("Test 2");            
                }
                if (o instanceof Sparql.Results){
                    //set results
                    System.out.println("Test 3");
                    
                    Sparql.Results results = (Sparql.Results) o;
                    allResults = results.getResult();
                    //Reset Lists
                    documentResults = new ArrayList<Sparql.Results.Result>();
                    imageResults = new ArrayList<Sparql.Results.Result>();
                    forumResults = new ArrayList<Sparql.Results.Result>();
                    clinicalResults = new ArrayList<Sparql.Results.Result>();
                    drugResults = new ArrayList<Sparql.Results.Result>();  
                    chemoResults = new ArrayList<Sparql.Results.Result>();
                    
                    for (int j = 0; j < allResults.size(); j++) {
                        Sparql.Results.Result res = allResults.get(j);
                        
                        Sparql.Results.Result.Binding binding = res.getBinding().get(0);
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/PublishedWork")) {
                            //add to document
                            documentResults.add(res);
                        }
                        
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/diagram2D")) {
                            //add them to images
                            imageResults.add(res);
                        }                        
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/ForumPost")) {
                            //add them to images
                            forumResults.add(res);
                        }                        
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/ClinicalTrial")) {
                            //add them to images
                            clinicalResults.add(res);
                        }
                        if (binding.getName().trim().startsWith("Drug")) {
                            //add them to drugs
                            drugResults.add(res);
                        }
                        if (binding.getName().trim().startsWith("ChemoAgent")) {
                            chemoResults.add(res);
                        }                        
                        
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//EoM
    
    
/*    
    private void parseMockXMLFile(String filename){
        try{
            JAXBContext jc = JAXBContext.newInstance( "eu.granatum.xsd" );
            Unmarshaller u = jc.createUnmarshaller();
            Object f = u.unmarshal( new File( "C:\\projects\\granatumbqi\\target\\test-classes\\"+filename ) );
            Sparql sparql =(Sparql) f;
            List<Object> list = sparql.getHeadOrResults();

            for (int i = 0; i < list.size(); i++) {
                Object o =  list.get(i);
                if (o instanceof Sparql.Head){
                    Sparql.Head head = (Sparql.Head) o;

                }
                if (o instanceof Sparql.Results){
                    //set results
                    Sparql.Results results = (Sparql.Results) o;
                    allResults = results.getResult();
                    //Reset Lists
                    documentResults = new ArrayList<Sparql.Results.Result>();
                    imageResults = new ArrayList<Sparql.Results.Result>();
                    forumResults = new ArrayList<Sparql.Results.Result>();
                    clinicalResults = new ArrayList<Sparql.Results.Result>();
                    drugResults = new ArrayList<Sparql.Results.Result>();  
                    chemoResults = new ArrayList<Sparql.Results.Result>();
                    
                    for (int j = 0; j < allResults.size(); j++) {
                        Sparql.Results.Result res = allResults.get(j);
                        
                        Sparql.Results.Result.Binding binding = res.getBinding().get(0);
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/PublishedWork")) {
                            //add to document
                            documentResults.add(res);
                        }
                        
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/diagram2D")) {
                            //add them to images
                            imageResults.add(res);
                        }                        
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/ForumPost")) {
                            //add them to images
                            forumResults.add(res);
                        }                        
                        if (binding.getUri().trim().startsWith("http://chem.deri.ie/granatum/ClinicalTrial")) {
                            //add them to images
                            clinicalResults.add(res);
                        }
                        if (binding.getName().trim().startsWith("Drug")) {
                            //add them to drugs
                            drugResults.add(res);
                        }
                        if (binding.getName().trim().startsWith("ChemoAgent")) {
                            chemoResults.add(res);
                        }                        
                        
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//EoM
*/

    public static void main(String[] args)
    {
        try{
        JAXBContext jc = JAXBContext.newInstance( "eu.granatum.xsd" );
        Unmarshaller u = jc.createUnmarshaller();
        Object f = u.unmarshal( new File( "D:\\tmp\\resp.xml" ) );
        Sparql sparql =(Sparql) f;
        List<Object> list = sparql.getHeadOrResults();
                    System.out.println( sparql.getHeadOrResults().isEmpty() );
                    System.out.println("TEst " + list.size() );
            for (int i = 0; i < list.size(); i++) {
                    System.out.println("TEst");                
                Object o =  list.get(i);
                if (o instanceof Sparql.Head){
                    Sparql.Head head = (Sparql.Head) o;
                    System.out.println("TEst");
                }
                if (o instanceof Sparql.Results){
                    System.out.println("TEst");
                    Sparql.Results results = (Sparql.Results) o;
                    List<Sparql.Results.Result> list2 = results.getResult();
                    for (int j = 0; j < list2.size(); j++) {
                        Object o1 =  list2.get(j);
                        System.out.println(o1.toString());
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
} //EoC
