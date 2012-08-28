package eu.granatum.beans;

import eu.granatum.xsd.Sparql;
import eu.granatum.xsd.Sparql.Results.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;





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
    private String annotations;
    private String drugs;
    private String searchterm;
    private List<Sparql.Results.Result> allResults;
    private List<Sparql.Results.Result> documentResults;
    private List<Sparql.Results.Result> imageResults;
    private List<Sparql.Results.Result> forumResults;
    private List<Sparql.Results.Result> clinicalResults;
    private List<Sparql.Results.Result> drugResults;
    

    final Logger logger = LoggerFactory.getLogger(SearchBean.class);

    public SearchBean() {
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

    public String getSearchterm() {
        return searchterm;
    }

    public void setSearchterm(String searchterm) {
        this.searchterm = searchterm;
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

        //mock Web Service Invocation
        try{
            logger.info( "Working path: "+new java.io.File(".").getCanonicalPath()  );
            System.out.println("Working path: "+new java.io.File(".").getCanonicalPath());

        } catch (Exception ex){
            logger.error(ex.toString());
        }


        if (annotations.trim().equalsIgnoreCase("0")){  //search1.xml
            parseMockXMLFile("sample1.xml");
        }
        if (annotations.trim().equalsIgnoreCase("1")){ //search2.xml
            parseMockXMLFile("sample2.xml");
        }

    }

    private void parseMockXMLFile(String filename){
        try{
            JAXBContext jc = JAXBContext.newInstance( "eu.granatum.xsd" );
            Unmarshaller u = jc.createUnmarshaller();
            Object f = u.unmarshal( new File( "/home/ubiadmin/appservers/jboss711final/bin/"+filename ) );
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
                        
                        
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//EoM


} //EoC
