package eu.granatum.beans;

import java.util.Collection;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class DataCloudSPARQLInterface
{
    private String query;
    private static final String endpointURL = "http://srvgal78.deri.ie:8080/sparql?output=xml&query=";
    
    public DataCloudSPARQLInterface(String query)
    {       
        this.query = new String(query);
        
        this.query = this.query.replaceAll(" ", "%20");
        this.query = this.query.replaceAll("\\+", "%20");
        this.query = this.query.replaceAll("#", "%23");
        this.query = this.query.replaceAll("\\[", "%5B");
        this.query = this.query.replaceAll("\\]", "%5D");        
        System.out.println("QUERY= " + this.query);
 
    }
    
    public void getAssociatedEntities()
    throws Throwable
    {
        StringBuffer msgsock = new StringBuffer();
        Collection collection = null;
        String line;
        FileWriter outFile;
        PrintWriter out;
        String responseMsg;
        
        URL targetURL = new URL(endpointURL + query);
        URLConnection connection = targetURL.openConnection();
        connection.setDoOutput(true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 

        while((line = reader.readLine()) != null)
        {
            if(line.startsWith("<sparql"))
                msgsock.append("<sparql>\n");
            else
                msgsock.append(line+"\n");
        }
        
        responseMsg = msgsock.toString().trim();
        reader.close();

//        outFile = new FileWriter("D:\\tmp\\resp.xml");
        outFile = new FileWriter("/home/ubiadmin/BQIfolder/resp.xml");        
        out = new PrintWriter(outFile);
        out.write(msgsock.toString());
        out.flush();
        out.close();
        
        return;
    }
    
    public static void main(String[] args)
    {
        try {        
        
            DataCloudSPARQLInterface d = new DataCloudSPARQLInterface("SELECT ?ChemoAgent ?Label ?smile ?sdf WHERE { ?ChemoAgent a <http://chem.deri.ie/granatum/ChemopreventiveAgent>. ?ChemoAgent <http://www.w3.org/2000/01/rdf-schema#label> ?Label. ?ChemoAgent <http://bio2rdf.org/ns/ns/ns/pubchem#SMILES> ?smile.?ChemoAgent <http://chem.deri.ie/granatum/sdf_file> ?sdf.}");
            
            d.getAssociatedEntities();
            
        } catch (Throwable ex) {
            Logger.getLogger(DataCloudSPARQLInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
