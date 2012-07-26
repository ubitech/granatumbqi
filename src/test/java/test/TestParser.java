package test;

import eu.granatum.xsd.Sparql;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pgouvas
 * Date: 7/24/12
 * Time: 5:07 PM
 */
public class TestParser {

    final Logger logger = LoggerFactory.getLogger(TestParser.class);


    @Test
    public void testSample1Exists(){
        System.out.println(1/0);
        try {
            logger.info( "Working path: "+new java.io.File(".").getCanonicalPath()  );
            BufferedReader in = new BufferedReader(new FileReader("src/test/resources/sample1.xml"));
            String str;
            while ((str = in.readLine()) != null) {
                logger.debug(str);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testJAXBonSample1(){
        try{
        JAXBContext jc = JAXBContext.newInstance( "eu.granatum.xsd" );
        Unmarshaller u = jc.createUnmarshaller();
        Object f = u.unmarshal( new File( "src/test/resources/sample1.xml" ) );
        Sparql sparql =(Sparql) f;
        List<Object> list = sparql.getHeadOrResults();

            for (int i = 0; i < list.size(); i++) {
                Object o =  list.get(i);
                if (o instanceof Sparql.Head){
                    Sparql.Head head = (Sparql.Head) o;

                }
                if (o instanceof Sparql.Results){
                    Sparql.Results results = (Sparql.Results) o;
                    List<Sparql.Results.Result> list2 = results.getResult();
                    for (int j = 0; j < list2.size(); j++) {
                        Object o1 =  list2.get(j);
                        logger.debug(o1.toString());
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testSample2Exists(){
        try {
            logger.info( "Working path: "+new java.io.File(".").getCanonicalPath()  );
            BufferedReader in = new BufferedReader(new FileReader("src/test/resources/sample2.xml"));
            String str;
            while ((str = in.readLine()) != null) {
                logger.debug(str);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void testJAXBonSample2(){
        try{
            JAXBContext jc = JAXBContext.newInstance( "eu.granatum.xsd" );
            Unmarshaller u = jc.createUnmarshaller();
            Object o = u.unmarshal( new File( "src/test/resources/sample2.xml" ) );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
