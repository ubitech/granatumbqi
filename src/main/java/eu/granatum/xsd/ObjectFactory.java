//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.09.18 at 11:50:12 AM EEST 
//


package eu.granatum.xsd;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.granatum.xsd package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.granatum.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Sparql.Results.Result.Binding }
     * 
     */
    public Sparql.Results.Result.Binding createSparqlResultsResultBinding() {
        return new Sparql.Results.Result.Binding();
    }

    /**
     * Create an instance of {@link Sparql.Results }
     * 
     */
    public Sparql.Results createSparqlResults() {
        return new Sparql.Results();
    }

    /**
     * Create an instance of {@link Sparql.Head.Variable }
     * 
     */
    public Sparql.Head.Variable createSparqlHeadVariable() {
        return new Sparql.Head.Variable();
    }

    /**
     * Create an instance of {@link Sparql.Results.Result }
     * 
     */
    public Sparql.Results.Result createSparqlResultsResult() {
        return new Sparql.Results.Result();
    }

    /**
     * Create an instance of {@link Sparql.Head }
     * 
     */
    public Sparql.Head createSparqlHead() {
        return new Sparql.Head();
    }

    /**
     * Create an instance of {@link Sparql }
     * 
     */
    public Sparql createSparql() {
        return new Sparql();
    }

}
